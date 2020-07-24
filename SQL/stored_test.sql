DROP PROCEDURE IF EXISTS GetTest;
DELIMITER //
CREATE PROCEDURE GetTest(IN var1 varchar(100))
	BEGIN
DECLARE temp_synonyms varchar(255);
       
        DECLARE v_not_found BOOL default FALSE;
		DECLARE data_cursor CURSOR FOR
		SELECT synonyms FROM datadict WHERE keywords = var1;
		
        DECLARE continue handler for not found set v_not_found := TRUE;
        
		OPEN data_cursor;
        cursor_loop: loop
			FETCH data_cursor INTO temp_synonyms;
			if v_not_found then 
				leave cursor_loop;
			end if;
            SET @sql_text1 = CONCAT('''%',temp_synonyms,'%''');
			SET @sql_text2 = concat('INSERT INTO tempTable SELECT id,''',var1, ''',message, ''OpenStack'',''',temp_synonyms,''',created_on,reviewer,author FROM comment_detail_openstack where message like ',@sql_text1,';'); 
			
			SELECT @sql_text2;
            PREPARE stmt1 FROM @sql_text2;
           
            EXECUTE stmt1;
			DEALLOCATE PREPARE stmt1;
            
            /*INSERT INTO tempTable SELECT comment_id, message FROM apatta.openstack_inline_comments where message like CONCAT('%',temp_synonyms,'%');*/
			/*SELECT temp_synonyms;*/
			
		end loop;
		
		CLOSE data_cursor;
		
	END//