CREATE DEFINER =`apatta`@`%`PROCEDURE `extractOpenStack`(IN var1 varchar(100))
DETERMINISTIC
BEGIN
DECLARE temp_synonyms varchar(255);
DECLARE);
DECLARE v_not_found BOOL default FALSE;
DECLARE data_cursor CURSOR FOR
SELECT synonyms FROM datadict WHERE keywords = var1;

DECLARE continue handler for not found set v_not_found 
:= TRUE;

OPEN data_cursor;
cursor_loop: loop
FETCH data_cursor INTO temp_synonyms;
if v_not_found then
   leave cursor_loop;
end if;
              SET@sql_text1 = CONCAT('''%',temp_synonyms,'%''');
 SET@sql_text2 = CONCAT('INSERT INTO tempOpenStack      SELECT id,''',
var1,''',message,''OpenStack'',''',temp_synonyms,''',
created_on,
reviewer,author FROM comment_detail_Openstack where message like 
',@sql_text1,';');

SELECT @sql_text2;
PREPARE stmt1 FROM @sql_text2;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;


end loop;

CLOSE data_cursor;
#Routine body goes here...

END;
