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
import java.util.ArrayList;
import static jsonparser.Jsonparser.printAll;

/**
 *
 * @author aziz
 */
public class JsonParserRequestDetail {
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//         ArrayList<String> retList = new ArrayList();
//         JsonFactory factory = new JsonFactory();
//
//       ObjectMapper mapper = new ObjectMapper(factory);
//       JsonNode rootNode = null;  
//       
//        FileInputStream fstream = new FileInputStream("/Users/aziz/Downloads/JSON-openstack/501-merged.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//
//        String strLine;
//
//        //Read File Line By Line
//        while ((strLine = br.readLine()) != null)   {
//        // Print the content on the console
//            rootNode = mapper.readTree(strLine);
//       // System.out.println (strLine);
//        String ret = parseRequestDetail(rootNode);
//        retList.add(ret);
//  
//        }
//        DBManager db = new DBManager();
//        db.executeData(retList);
//    }
    
    public String parseRequestDetail(JsonNode rootNode){
        String ret = "";
        String id = "";
        String gerrit_id = Utility.nodeString(rootNode.get("id"));
        String request_id = Utility.nodeString(rootNode.get("number"));
        id = gerrit_id +"_"+request_id;
        String project = Utility.nodeString(rootNode.get("project"));
        String branch = Utility.nodeString(rootNode.get("branch"));
        String topic = Utility.nodeString(rootNode.get("topic"));
        String subject = Utility.nodeString(rootNode.get("subject"));
        String status = Utility.nodeString(rootNode.get("status"));
        String created = Utility.longToDateString(rootNode.get("createdOn"));
        String updated = Utility.longToDateString(rootNode.get("lastUpdated"));
        String ownerUsername  = "";
        String number_patches = "";
         String sql = "";
        
        JsonNode ownerNode = rootNode.get("owner");
        if(ownerNode != null){
             ownerUsername = Utility.nodeString(ownerNode.get("username"));
        }
 
        JsonNode pathSets = rootNode.get("patchSets");
        if(pathSets != null && pathSets.isArray()){
            int i =0;
            for(JsonNode objNode : pathSets)
                i++;
            number_patches = ""+i;
            
        }
        if(id.length() > 1)
            sql = String.format("INSERT INTO request_detail (id,gerrit_id,request_id,project,branch,topic,subject,status,created,updated,owner_username,number_patches) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')", id,gerrit_id,request_id,project,branch,topic,subject,status,created,updated,ownerUsername,number_patches );
        
        ret = sql;
        //System.out.println(sql);
        return ret;
        
    }
}
