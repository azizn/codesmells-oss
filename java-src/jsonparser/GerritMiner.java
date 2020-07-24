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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author aziz
 */
public class GerritMiner {
    public static void main(String[] args){
       GerritMiner miner = new GerritMiner();
       if(args.length > 0)
        miner.startParser(args[0]);
       else
           System.exit(1);
    }
    
    private void startParser(String path){
   
       FileInputStream fstream = null;
       File folder = null;
       File[] listFiles;
       ArrayList<String> fileList = new ArrayList();
       BufferedReader br = null;
       //Iteratate files in the folder
       folder = new File(path);
       listFiles = folder.listFiles(new GerritFileFilter());
       for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isFile()) {
                 fileList.add(listFiles[i].getAbsolutePath());
             } 
        }
       //Read a file
        for(String fileName : fileList){
            try{
             fstream = new FileInputStream(fileName);
             br = new BufferedReader(new InputStreamReader(fstream));
             extractLine(br);
             System.out.println("-------------"+fileName+" was successfully parsed.");
            }catch(FileNotFoundException fex){
                fex.printStackTrace();
            }           
        }
    }
    
    private void extractLine(BufferedReader br){
        String strLine;
        ArrayList<String> retDetail = null;
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = null; 
        ArrayList<String> retList = new ArrayList();
        JsonParserRequestDetail detail = new JsonParserRequestDetail();
        JsonParserCommentHistory history = new  JsonParserCommentHistory();
        JsonParserCommentDetail comments = new JsonParserCommentDetail();
        //Read File Line By Line
        try{
             while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                 rootNode = mapper.readTree(strLine);
                // System.out.println (strLine);
                String ret = detail.parseRequestDetail(rootNode);
                retList.add(ret);
                ret = history.parseComments(rootNode);
                retList.add(ret);
                retDetail = comments.parseCommentDetail(rootNode);
                for(String dd : retDetail){
                    retList.add(dd);
                }
                
            }
             addData(retList);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void addData(ArrayList<String> list){
        DBManager db = new DBManager();
        db.executeData(list);
        
    }
}
