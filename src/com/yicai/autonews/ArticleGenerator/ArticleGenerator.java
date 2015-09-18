package com.yicai.autonews.ArticleGenerator;

import java.util.ArrayList;
import java.util.List;

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
import com.yicai.autonews.cvsfile.QuotationCsvFileExport;



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
	    QuotationDao quotationDao = new QuotationDao();
			List<String> list = quotationDao.getOneQuotations(2015, 8);
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i));
		}
		QuotationCsvFileExport quotationExport = new QuotationCsvFileExport() ;
		String outpath = "quotations.csv";
		quotationExport.exportQuotationsToCsv(list, outpath);
		
        Article analysisOfStats = new AnalysisDao().getOneAnalysisArticle(year, month);
		TemplateWriter templateWriter = new TemplateWriter(historicalMonthCPIList, historicalMonthPPIList, curMonthCPI,
				curMonthPPI);
		
		String cpiCommentFile = "cpiComments.csv";
		String ppiCommentFile = "ppiComments.csv";
		
		Article newsArticle =  new ArticleAssemble(analysisOfStats, templateWriter,
				cpiCommentFile, ppiCommentFile).articleAssemble();
		
		List<Section> sectionList = newsArticle.getSections();
		
		//String strOfArticle = "";
		System.out.println(newsArticle.getTitle());
		for(int i = 0; i < sectionList.size(); i++){
			Section section = sectionList.get(i);
			System.out.println(section.getSubTitle());
			List<Paragraph> paragraphList = section.getParagraphs();
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
	

}
