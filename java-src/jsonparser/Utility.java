/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsonparser;

import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author aziz
 */
public class Utility {
    
    
    public static String longToDateString(JsonNode node){
         String ret = "";
        if(node != null){
             Date d = new Date(Long.parseLong(node.asText())*1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            ret = dateFormat.format(d);
        }    
        return ret;  
    }
    
    public static String nodeString(JsonNode node){
       String newStr = "";
        if(node == null){
            return "";
        }else{
            String old = node.asText();
            newStr = old;
            if(newStr.contains("'")){
               
                newStr = old.replace("'", "''");
               
            }
            
            if(newStr.contains("\\")){
                newStr = newStr.replace("\\","\\\\");
            }
               
            return newStr;
        }
    }
    
    public static String firstLine(String str){
        String ret = "";
        if(str != null){
            String[] lines = str.split("\\r?\\n");
            if(lines.length > 0)
                ret = lines[0];
        }
        return ret;
        
    }
}
