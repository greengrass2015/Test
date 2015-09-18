package com.yicai.autonews.article.assemble;

import java.util.ArrayList;
import java.util.List;

import com.github.cyl.autonews.dao.cpi.CPIDao;
import com.github.cyl.autonews.dao.ppi.PPIDao;
import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Section;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
import com.github.cyl.autonews.pojo.ppi.MonthPPI;
import com.yicai.autonews.article.writer.Reformat;
import com.yicai.autonews.article.writer.TemplateWriter;
import com.yicai.autonews.staticclass.MyDate;
import com.yicai.autonews.article.writer.EconomicComments;

public class ArticleAssemble {
	/*private TemplateWriter templateWriter = new TemplateWriter(historicalMonthCPIList,
			historicalMonthPPIList, curMonthCPI, curMonthPPI);*/
	//private Article newsArticle;
	private Article analysisOfStats;
	private TemplateWriter templateWriter;
	//String cpiCommentsFile = "gFinalResult.csv";
	private String cpiCommentsFile ;
	private String ppiCommentsFile ;
	public ArticleAssemble(){
	}
	public ArticleAssemble(Article analysisOfStats, TemplateWriter templateWriter, 
			String cpiCommentsFile, String ppiCommentsFile){
		this.analysisOfStats = analysisOfStats;
		this.templateWriter = templateWriter;
		this.cpiCommentsFile = cpiCommentsFile;
		this.ppiCommentsFile = ppiCommentsFile;
	}
	public Article articleAssemble( ){
		Article newsArticle = new Article();
		newsArticle.setTitle(templateWriter.articleTitleGenerator( ));
		List<Section> sectionList = new ArrayList<Section>();
		List<Section> sectionListTemp = new ArrayList<Section>();
		//第一个subsection, 标题为null, 只有第一段 firstParagraph
		String subTitle = null;
		List<Paragraph> paragraphList = new ArrayList<Paragraph>();
		System.out.println(templateWriter.firstParagraphGenerator());
		paragraphList.add(templateWriter.firstParagraphGenerator());
		Section sectionTemp = new Section(null, paragraphList); //标题为空
		sectionList.add(0, sectionTemp); // 组装第一个section, index 为0
	   
		
		//第二个subsection, 标题由cpiTitleGenerator生成
		//第二个subsection由统计师的CPI详细解读报告改写 和专家评论组成。
		subTitle = templateWriter.cpiTitleGenerator();
		sectionListTemp = new Reformat().reformatAnalysis(analysisOfStats.getSections());
		//把改写后的第二个 （index=1）subsection取出来
		paragraphList = new ArrayList<Paragraph>();//
		paragraphList.addAll(sectionListTemp.get(1).getParagraphs());
		// 再添加专家评论，此处为CPI专家评论
		//String cpiCommentsFile = "cpiComments.csv";
		//String cpiCommentsFile = "gFinalResult.csv";
		EconomicComments cpiComments = new EconomicComments();
		paragraphList.addAll(cpiComments.indicatorComments(cpiCommentsFile));
		
		
		sectionTemp = new Section(subTitle, paragraphList); 
		//sectionList.add(1, element);
		sectionList.add(1, sectionTemp); // 组装第一个section, index 为0
		paragraphList = new ArrayList<Paragraph>();
		
		//第三个subsection, 标题由ppiTitleGenerator生成
		//第三个subsection由统计师的PPI详细解读报告改写 和专家评论组成。
		subTitle = templateWriter.ppiTitleGenerator();
	
		//把改写后的第3个（index=2）subsection取出来
		paragraphList.addAll(sectionListTemp.get(2).getParagraphs());
		// 再添加专家引用
		
		sectionTemp = new Section(subTitle, paragraphList); //标题为空
		//sectionList.set(2, sectionTemp); // 组装第一个section, index 为0
		sectionList.add(2, sectionTemp);
		
		
		
		newsArticle.setSectinos(sectionList);
		return newsArticle;
		
		
	}

}
