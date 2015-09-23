package com.yicai.autonews.articlegenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;

import com.github.cyl.autonews.dao.analysis.AnalysisDao;
import com.github.cyl.autonews.dao.cpi.CPIDao;
import com.github.cyl.autonews.dao.ppi.PPIDao;
import com.github.cyl.autonews.dao.quotation.QuotationDao;
import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Section;
import com.github.cyl.autonews.pojo.analysis.Sentence;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
import com.github.cyl.autonews.pojo.ppi.MonthPPI;
import com.yicai.autonews.article.assemble.ArticleAssemble;
import com.yicai.autonews.article.writer.TemplateWriter;
import com.yicai.autonews.cvsfile.CsvFileExport;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArticleGenerator {
	

	public Article newsArticle(int year, int month){
		
		MonthCPI curMonthCPI = new CPIDao().getOneMonthCPI(year, month);
		// monthCPITemp;
		List<MonthCPI> historicalMonthCPIList = new ArrayList<MonthCPI>();
		for (int i = 1; i < month; i++) {
			MonthCPI monthCPITemp = new CPIDao().getOneMonthCPI(year, i);
			historicalMonthCPIList.add(monthCPITemp);
		}
		MonthPPI curMonthPPI = new PPIDao().getOneMonthPPI(year, month);
		// MonthPPI monthPPITemp;
		List<MonthPPI> historicalMonthPPIList = new ArrayList<MonthPPI>();
		for (int i = 1; i < month; i++) {
			MonthPPI monthPPITemp = new PPIDao().getOneMonthPPI(year, i);
			historicalMonthPPIList.add(monthPPITemp);
		}
	   
        
		TemplateWriter templateWriter = new TemplateWriter(historicalMonthCPIList, historicalMonthPPIList, curMonthCPI,
				curMonthPPI);
	    List<String> quotationList = new QuotationDao().getOneQuotations(year, month);
	    
		Article analysisOfStats = new AnalysisDao().getOneAnalysisArticle(year, month);
		csvFilePrepForQuotationSelection(quotationList, analysisOfStats);
		
		try{  
            //System.out.println("start");
            String[] cmd = new String[2];
            //String str="D:/workspace/python/Label.py";
            String str="/workspace/python/Label.py";
           // String str="D:\\code\\java\\DownLoadFile\\wenbenwajue\\src\\Label.py";
            cmd[0] = "python";
            cmd[1] = str;
            
            Process pr = Runtime.getRuntime().exec(cmd);  
              
            BufferedReader in = new BufferedReader(new  
                    InputStreamReader(pr.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);  
            }  
            in.close();  
            pr.waitFor();  
          //  System.out.println("end");  
    } catch (Exception e){  
                e.printStackTrace();  
            }   
		
		
		String cpiCommentFile = "/workspace/python/CPIcomments.csv";
		String ppiCommentFile = "/workspace/python/PPIcomments.csv";
		/*
		String cpiCommentFile = "d:/workspace/python/CPIcomments.csv";
		String ppiCommentFile = "d:/workspace/python/PPIcomments.csv";*/
		
		
		Article newsArticle =  new ArticleAssemble(analysisOfStats, templateWriter,
				cpiCommentFile, ppiCommentFile).articleAssemble();
		
		List<Section> sectionList = new ArrayList<Section>();
		List<Paragraph> paragraphList = new ArrayList<Paragraph>();		
		sectionList = newsArticle.getSections();
		
		System.out.println(newsArticle.getTitle());
		for(int i = 0; i < sectionList.size(); i++){
			Section section = sectionList.get(i);
			System.out.println(section.getSubTitle());
			paragraphList = section.getParagraphs();
			for(int j = 0; j < paragraphList.size(); j++){
				Paragraph paragraph = paragraphList.get(j);
				List<Sentence> sentenceList = paragraph.getSentences();
				for(int k = 0; k < sentenceList.size(); k++){
					Sentence sentence = sentenceList.get(k);
					System.out.print(sentence.getSentence());
				}
				System.out.print("\n");
			}
			
		}
		return newsArticle;
	}
	public void csvFilePrepForQuotationSelection(List<String> quotationList, Article analysisOfStats){

	    String outPath = "";
				for(int i = 0; i < quotationList.size(); i++){
					System.out.println(quotationList.get(i));
			}
			CsvFileExport quotationExport = new CsvFileExport() ;
			//String outpath = "c:/myFile/quotations.csv";
			CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator('\n'); // 每条记录间隔符
			
			String outpath = "/workspace/python/quotations.csv";
			//String outpath = "d:/workspace/python/quotations.csv";
		    quotationExport.exportQuotationsToCsv(quotationList, outpath,format);	    	
	       
		    format = CSVFormat.DEFAULT.withRecordSeparator(null); // 每条记录间隔符
			CsvFileExport analysisExport = new CsvFileExport() ;
			//outpath = "c:/myFile/yuqiumeiCPI.csv";
			//outpath = "d:/workspace/python/yuqiumeiCPI.csv";
			outpath = "/workspace/python/yuqiumeiCPI.csv";
			List<Section> sectionList = analysisOfStats.getSections();
			List<Paragraph> paragraphList = sectionList.get(1).getParagraphs();
			List<String> strListCPI = new ArrayList<String>();
			for (int i = 0; i < paragraphList.size(); i++){
				List<Sentence> sentenceList = paragraphList.get(i).getSentences();
				for (int j = 0; j < sentenceList.size(); j++){
					String str = sentenceList.get(j).getSentence();
					strListCPI.add(str);
				}
			}
		    quotationExport.exportQuotationsToCsv(strListCPI, outpath, format);	
		    //
		    
			analysisExport = new CsvFileExport() ;
			//outpath = "c:/myFile/yuqiumeiPPI.csv";
			//outpath = "d:/workspace/python/yuqiumeiPPI.csv";
		   outpath = "/workspace/python/yuqiumeiPPI.csv";
			paragraphList = sectionList.get(2).getParagraphs();
			List<String> strListPPI = new ArrayList<String>();
			for (int i = 0; i < paragraphList.size(); i++){
				List<Sentence> sentenceList = paragraphList.get(i).getSentences();
				for (int j = 0; j < sentenceList.size(); j++){
					String str = sentenceList.get(j).getSentence();
					strListPPI.add(str);
				}
			}
		    quotationExport.exportQuotationsToCsv(strListPPI, outpath, format);	
	}

}
