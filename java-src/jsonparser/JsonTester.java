/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsonparser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author aziz
 */
public class JsonTester {
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
            //System.out.println(fieldName + " :");
            
            //printAll(fieldValue);
         } else {
            String value = fieldValue.asText();
            if(fieldName.equals("id") ){
            
                 System.out.println(value);
            }
           
         }
     }
}
    
}
