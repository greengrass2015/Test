package com.yicai.autonews.article.writer;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.cyl.autonews.dao.analysis.AnalysisDao;
import com.github.cyl.autonews.dao.cpi.CPIDao;
import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.analysis.Clause;
import com.github.cyl.autonews.pojo.analysis.Section;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Sentence;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;

import com.yicai.autonews.staticclass.MyDate;


public class Reformat {
	//private AnalysisDao analysisDao = new AnalysisDao();
	//private CPIDao cpiDao = new CPIDao();
	public List<Section> reformatAnalysis(List<Section> sectionList){

		List<Section> sectionListTemp = new ArrayList<Section>();
		Section sectionTemp = new Section();
		List<Paragraph> paragraphListTemp = new ArrayList<Paragraph>();
		
		for (int i = 0; i < sectionList.size(); i++ ){
			Section section = sectionList.get(i);
			String title = section.getSubTitle();
			//System.out.println("subscetion" + i + "的副标题是：" + title);
			List<Paragraph> paragraphList = section.getParagraphs();
		//	System.out.println("Title 是否含有CPI"+title.matches("CPI"));
			//if (i == 1|| title.matches("CPI")||title.matches("居民消费价格"))
			int idx1 = -2, idx2 = -2, idx3 = -2, idx4 = -2;
			if(title != null){
				idx1 = title.indexOf("CPI");
				idx2 = title.indexOf("居民消费价格");
				idx3 = title.indexOf("PPI");
				idx4 = title.indexOf("工业生产者出厂价格");
			}
			if (i == 1 || idx1 >= 0 || idx2 >= 0){
				for (int j = 0; j < paragraphList.size(); j++){
					Paragraph paragraph = paragraphList.get(j);
	
					if (j == 0 ){
						String[] str ={"国家统计局城市司高级统计师余秋梅分析", "国家统计局城市司高级统计师余秋梅表示", "国家统计局城市司高级统计师余秋梅指出"
								"国家统计局城市司高级统计师余秋梅认为", "国家统计局城市司高级统计师余秋梅在解读CPI数据时认为"};
						Random r = new Random();
						int index = r.nextInt(str.length);
						Clause clauseTemp = new Clause(str[index]);
						List<Sentence> sentenceList = paragraph.getSentences();
						//System.out.println(clauseTemp.getClause());
						//System.out.println(sentenceList);
						List<Clause> clauseList = sentenceList.get(0).getClauses();
						//System.out.println("添加前"+clauseList);
						clauseList.add(0,clauseTemp);
						String sentence = "";
						for(int k = 0; k < clauseList.size(); k++){
							if(k != clauseList.size()-1){
								sentence = sentence + clauseList.get(k).getClause()+'，';
							}else{
								sentence = sentence + clauseList.get(k).getClause()+'。';
							}
						}
						sentenceList.get(0).setClauses(clauseList);
						sentenceList.get(0).setSentence(sentence); 
						//设置句子的属性
						for(int l = 0; l < sentenceList.size(); l++){
							sentenceList.get(l).setType(1);
						}
		
						paragraphListTemp.add(new Paragraph(sentenceList));
						//sectionTemp.set(paragraphTemp);

					}else if (j ==1){
						String[] str = {"她表示", "她分析", "她认为", "余秋梅称"};
						Random r = new Random();
						int index = r.nextInt(str.length);
						Clause clauseTemp = new Clause(str[index]);
						List<Sentence> sentenceList = paragraph.getSentences();
						List <Clause> clauseList = sentenceList.get(0).getClauses();
						clauseList.add(0, clauseTemp);
						String sentence = "";
						for(int l = 0; l < clauseList.size(); l++){
							if(l != clauseList.size()-1){
								sentence =sentence+clauseList.get(l).getClause()+'，';
							}else{
								sentence =sentence+clauseList.get(l).getClause()+'。';
							}
						}
						sentenceList.get(0).setClauses(clauseList);
						sentenceList.get(0).setSentence(sentence); 
						//设置句子的属性
						for(int l = 0; l < sentenceList.size(); l++){
							sentenceList.get(l).setType(1);
						}
						paragraphListTemp.add(new Paragraph(sentenceList));
					}else{
						paragraphListTemp.add(paragraph);
					}
					
				}
				sectionTemp.setParagraphs(paragraphListTemp);
				sectionListTemp.add(sectionTemp);
			}else if (i == 2|| idx3 >= 0 || idx4 >= 0){
				for(int k = 0; k <section.getParagraphs().size(); k++ ){
					List<Sentence> sentenceList = section.getParagraphs().get(k).getSentences();
					for(int l = 0; l < sentenceList.size(); l++){
						sentenceList.get(l).setType(1);
					}
				}
				sectionListTemp.add(section);
			}else{
				for(int k = 0; k <section.getParagraphs().size(); k++ ){
					List<Sentence> sentenceList = section.getParagraphs().get(k).getSentences();
					for(int l = 0; l < sentenceList.size(); l++){
						sentenceList.get(l).setType(1);
					}
				}
				sectionListTemp.add(section);
			}
			
		}
		return sectionListTemp;
	}

}
