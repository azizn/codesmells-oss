/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsonparser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aziz
 */
public class Jsonparser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
       JsonFactory factory = new JsonFactory();

       ObjectMapper mapper = new ObjectMapper(factory);
       JsonNode rootNode = null;  
       
        FileInputStream fstream = new FileInputStream("/Users/aziz/Downloads/JSON-openstack/1-merged.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
        // Print the content on the console
            rootNode = mapper.readTree(strLine);
       // System.out.println (strLine);
        printAll(rootNode);
    }

        //Close the input stream
        br.close();
        

//       Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
//       while (fieldsIterator.hasNext()) {
//
//           Map.Entry<String,JsonNode> field = fieldsIterator.next();
//           System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
//       }
    }
    
    
    public static void printAll(JsonNode node) {
     Iterator<String> fieldNames = node.fieldNames();
     while(fieldNames.hasNext()){
         String fieldName = fieldNames.next();
         JsonNode fieldValue = node.get(fieldName);
       
         if (fieldValue.isObject()) {
            System.out.println(fieldName + " :");
            
            printAll(fieldValue);
         } else if(fieldValue.isArray()){
             if(fieldName.equals("comments")){
              
                 for(JsonNode objNode : fieldValue){
                     if(objNode.isObject()){
                        Iterator<String> fnames = objNode.fieldNames();
                        while(fnames.hasNext()){
                             String ff = fnames.next();
                             if(ff.equals("reviewer")){
                               
                               JsonNode reviewerNode = objNode.get(ff);
                               if(reviewerNode.isObject()){
                                   printAll(reviewerNode);
                               }
                              
                                
                         }
                     }
                   }
                     
                    JsonNode val = objNode.get("timestamp");
                    System.out.println("timestamp :"+val.asText());
                    val = objNode.get("message");
                    System.out.println("message :"+val.asText());
                    System.out.println("--------------------------------------");
                    
                 }
             }
             // System.out.println(fieldName+":");
             // for(JsonNode objNode : fieldValue){
            //      printAll(objNode);
             // }
         } else {
            String value = fieldValue.asText();
            if(fieldName.equals("timestamp") || fieldName.equals("createdOn") || fieldName.equals("lastUpdated") ){
                 Date d = new Date(Long.parseLong(value)*1000);
                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                 System.out.println(fieldName + " : " + dateFormat.format(d));
            }else{
                 System.out.println(fieldName + " : " + value);
            }
           
         }
     }
}
    
}
