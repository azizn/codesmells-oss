/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsonparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author aziz
 */
public class DBManager {
    public void executeData(ArrayList<String> list){
         Connection conn = null;
         Statement stmt = null;
         String errCommand = "";
         try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://172.19.200.21:3306/apatta?user=aziz&password=v:ul2014");
            conn.setAutoCommit(true);
            stmt = conn.createStatement();
            for(String command : list){
                errCommand = command;
               try{
                if(command.length() > 0)
                    stmt.executeUpdate(command);
               }catch(SQLException seq){
                   System.out.println("-#-#-#-#-#-#-#-#-#`-"+seq.getMessage());     
               }
               // stmt.addBatch(command);
            }         
          // stmt.executeBatch();
          // conn.commit();          
        } catch (Exception ex) {       
            System.out.println(ex.getMessage());    
        }finally{
             try {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException sqlEx) {sqlEx.printStackTrace();} 
                stmt = null;
                conn = null;        
        }
    }
}
