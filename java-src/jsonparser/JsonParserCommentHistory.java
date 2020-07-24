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

/**
 *
 * @author aziz
 */
public class JsonParserCommentHistory {
//     public static void main(String[] args) throws FileNotFoundException, IOException {
//         ArrayList<String> retList = new ArrayList();
//       JsonFactory factory = new JsonFactory();
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
//        String ret = parseComments(rootNode);
//        retList.add(ret);
//  
//        }
//        DBManager db = new DBManager();
//        db.executeData(retList);
//               
//     }
        
        public String parseComments(JsonNode rootNode){
            String ret = "";
            String id = "";
            String gerrit_id = Utility.nodeString(rootNode.get("id"));
            String request_id = Utility.nodeString(rootNode.get("number"));
            String link_id = gerrit_id+"_"+request_id;
            String path_set = "";
            String status = "";
            String timestamp = "";
            String reviewer="";
            String sql = "";
            JsonNode commentNodes = rootNode.get("comments");
            int i = 1;
            if(commentNodes != null && commentNodes.isArray()){
                for(JsonNode commentNode : commentNodes){
                    id = gerrit_id+"_"+request_id+"_"+i;
                    timestamp = Utility.longToDateString(commentNode.get("timestamp"));
                    reviewer = getReviewerName(commentNode.get("reviewer"));
                    String message = Utility.firstLine(commentNode.get("message").asText());
                 //  System.out.println(message);
                    String[] msg = message.split(":");
                    if(msg.length == 2){
                        path_set = msg[0];
                        status = msg[1];
                    }else{
                        path_set = message;
                        status = "";
                    }
                    i++;
                   
                     if(id.length() > 0)
                        sql = String.format("INSERT INTO comment_history (id, link_id,patch_set,status,timestamp,reviewer) values('%s','%s','%s','%s','%s','%s')",id,link_id,path_set,status,timestamp,reviewer);
                   
                     ret = sql;
                //   System.out.println(sql);
                }
                
            }  
            return ret;
        }
        
        private String getReviewerName(JsonNode node){
            String ret = "";
            if(node != null){
               ret = Utility.nodeString( node.get("username"));
            }
            return ret;
        }
   
}
