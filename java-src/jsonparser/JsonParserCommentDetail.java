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
public class JsonParserCommentDetail {
//    public static void main(String[] args) throws FileNotFoundException, IOException { 
//        ArrayList<String> retList = new ArrayList();
//    JsonFactory factory = new JsonFactory();
//
//       ObjectMapper mapper = new ObjectMapper(factory);
//       JsonNode rootNode = null;  
//       
//        FileInputStream fstream = new FileInputStream("/Users/aziz/Downloads/JSON-openstack/2.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//
//        String strLine;
//
//        //Read File Line By Line
//        while ((strLine = br.readLine()) != null)   {
//        // Print the content on the console
//            rootNode = mapper.readTree(strLine);
//       // System.out.println (strLine);
//            String ret = parseCommentDetail(rootNode);
//            retList.add(ret);
//  
//        }
//        DBManager db = new DBManager();
//        db.executeData(retList);
//    }
    
    public ArrayList<String> parseCommentDetail(JsonNode rootNode){
        ArrayList<String> ret = new ArrayList();
        String gerrit_id = Utility.nodeString(rootNode.get("id"));
        String request_id = Utility.nodeString(rootNode.get("number"));
        String link_id = gerrit_id+"_"+request_id;
        String id = "";
        String number = "";
        String revision = "";
        String uploader = "";
        String author = "";
        String reviewer = "";
        String file = "";
        String line = "";
        String message = "";
        String createdOn = "";
        JsonNode commentNodes = null;
        String sizeInsertions = "0";
        String sizeDeletions = "0";
        String kind = "";
        String sql = "";
        
        JsonNode patchSets = rootNode.get("patchSets"); 
        if(patchSets != null && patchSets.isArray()){
             //  System.out.println("*****");
            for(JsonNode patchSet : patchSets){
                number = Utility.nodeString(patchSet.get("number"));
                
                revision = Utility.nodeString(patchSet.get("revision"));
                uploader = getUploaderName(patchSet.get("uploader"));
                createdOn = getPatchCreatedOn(patchSet);
                author = getAuthorName(patchSet.get("author"));
               
                sizeInsertions = Utility.nodeString(patchSet.get("sizeInsertions"));
                sizeDeletions = Utility.nodeString(patchSet.get("sizeDeletions"));
                kind = Utility.nodeString(patchSet.get("kind"));
                if(( commentNodes = patchSet.get("comments")) != null){ 
                //    if(commentNodes.isArray()){
                        int i =  1;
                        for(JsonNode commentNode : commentNodes){
                            id = revision+"_"+i;
                            file = Utility.nodeString(commentNode.get("file"));
                            line = Utility.nodeString(commentNode.get("line"));
                            message = Utility.nodeString(commentNode.get("message"));
                            reviewer = getReviewerName(commentNode.get("reviewer"));
                            i++;
                            if(id.length() > 0)
                                sql = String.format("INSERT INTO comment_detail (id,link_id,patchset_number,revision,uploader,created_on,author,reviewer,file,line,message,sizeInsertions,sizeDeletions,kind) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",id,link_id,number,revision,uploader,createdOn, author,reviewer,file,line,message,sizeInsertions,sizeDeletions,kind); 
                            
                      //      System.out.println(sql);
                            ret.add(sql);
                        }
                }
              
            }
        }
        return ret;
    }
    
    private String getPatchCreatedOn(JsonNode node){
        String ret = "";
        if(node != null){
            ret = Utility.longToDateString(node.get("createdOn"));
        }
        return ret;
    }
    
    private String getUploaderName(JsonNode node){
            String ret = "";
            if(node != null){
               ret = Utility.nodeString( node.get("username"));
            }
            return ret;
        }
    
    private String getAuthorName(JsonNode node){
            String ret = "";
            if(node != null){
               ret = Utility.nodeString( node.get("username"));
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
