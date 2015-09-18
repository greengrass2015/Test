package com.yicai.autonews.article.writer;
import com.yicai.autonews.cvsfile.CSVFileUtil;

import java.util.ArrayList;
import java.util.List;

import com.github.cyl.autonews.pojo.analysis.Clause;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Sentence;;
public class EconomicComments {
	private CSVFileUtil csvFileUtil = new CSVFileUtil();
	private String CLAUSE_SPLITER = ",|，|;|；|:|：";
	public List<Paragraph> indicatorComments(String indicatorCommentsCsvFile){
		List<Paragraph> paragraphList = new ArrayList<Paragraph>();
	//	List<Sentence> sentenceList = new ArrayList<Sentence>();
		
		List<String> strList = new ArrayList<String>();
		strList = csvFileUtil.csvFileRead(indicatorCommentsCsvFile);
		for (int i = 0; i < strList.size(); i++){
			String str = strList.get(i);
			/*assembleClauses(str.split(CLAUSE_SPLITER));*/
			Sentence sentence = new Sentence(str);
			List<Sentence> sentenceList = new ArrayList<Sentence>();
			sentenceList.add(0, sentence);
			Paragraph paragraph = new Paragraph(sentenceList);
			paragraphList.add(i, paragraph);
		}

		return paragraphList;
	}

	private List<Clause> assembleClauses(String[] arr) {
		List<Clause> clauses = new ArrayList<Clause>();
		for (String str : arr) {
			clauses.add(new Clause(str));
		}
		return clauses;
	}
}
