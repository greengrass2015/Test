package com.yicai.autonews.cvsfile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import com.github.cyl.autonews.dao.quotation.QuotationDao;
import com.yicai.autonews.staticclass.MyDate;

 
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.util.ArrayList;
import java.util.List;  
  
public class CSVFileUtil {  
	//String inPath = "gFinalResult.csv";
    public List<String> csvFileRead(String inPath){
    	List<String> strList = new ArrayList<String>();
		try{ 
	        DataInputStream in = new DataInputStream(new FileInputStream(new File(inPath))); 
	        BufferedReader br= new BufferedReader(new InputStreamReader(in,"GBK"));
	        String temp = "";  
	       
	        int i = 0;  
	        while((temp=br.readLine()) != null){  
	            System.out.print(++i + "  ");  
	            System.out.println(temp); 
	            strList.add(temp);
	        }  
	        br.close();  
	        //fr.close();  
	        }catch(Exception e){ 
	        e.printStackTrace(); 
	        }
		return strList;
    }
}  