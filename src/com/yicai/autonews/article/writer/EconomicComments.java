package com.yicai.autonews.article.writer;
import com.yicai.autonews.cvsfile.CSVFileUtil;

import java.util.ArrayList;
import java.util.List;

import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.analysis.Clause;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Sentence;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
public class EconomicComments {
	private CSVFileUtil csvFileUtil = new CSVFileUtil();
	private String SENTENCE_SPLITER = "。";
	private String CLAUSE_SPLITER = "，|;|；|:|：";
	public List<Paragraph> indicatorComments(String indicatorCommentsCsvFile, Article analysisOfStats){
		List<Paragraph> paragraphList = new ArrayList<Paragraph>();
		
		List<String> strList = new ArrayList<String>();
		strList = csvFileUtil.csvFileRead(indicatorCommentsCsvFile);
		/*for (int i = 0; i < strList.size(); i++){
			String str = strList.get(i);
			Sentence sentence = new Sentence(str);
			List<Sentence> sentenceList = new ArrayList<Sentence>();
			sentenceList.add(0, sentence);
			Paragraph paragraph = new Paragraph(sentenceList);
			paragraphList.add(i, paragraph);
				
			}*/
		
		String strKey = analysisOfStats.getMonth() + "月";
		for (int i = 0; i < strList.size(); i++){
			String str = strList.get(i);
			List<Sentence> sentenceList = new ArrayList<Sentence>();
			sentenceList = reorganizeEachComments(str,  strKey);
			for(int j = 0; j < sentenceList.size(); j++){
				sentenceList.get(j).setType(2);   // Type 2: 代表经济学家评论
			}
			Paragraph paragraph = new Paragraph(sentenceList);
			paragraphList.add(i, paragraph);
			
		}

		return paragraphList;
	}
	public List<Sentence> reorganizeEachComments(String str, String strKey){
		String[] sentenceArr = str.split(SENTENCE_SPLITER);	// 把每一评论分割成以句子为单位
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		String reorganizedComment = "";
		for(int j = 0; j < sentenceArr.length; j++){
			String[] clauseArr = new String[]{""};
			//List<Clause> clauseList = new ArrayList<Clause>();
			clauseArr = sentenceArr[j].split(CLAUSE_SPLITER);	//每一句子分割成从句
            int index = throwAwayClause(clauseArr, strKey); //返回需要去掉的从句所在的index，或者-1如果无需处理的话
            if (index != -1){// 有从句
                // 如果有从句需要扔掉，判断从句的位置，如果从句前面至少有2句话或者该从句所属句子非评论的组后一句，
                // 则保留此从句之前的所有从句，否则，把整个句子的所有从句都扔掉。
            	if( index > 1 || j != sentenceArr.length-1){
            		for(int k = 0; k < index; k++){
            			reorganizedComment = reorganizedComment + clauseArr[k]+',';
            		}
            	}
            }else{
            	reorganizedComment = reorganizedComment + sentenceArr[j] + '.';           	
            }

		}
    	Sentence sentence = new Sentence(reorganizedComment);

		sentenceList.add(sentence);
		
		return sentenceList;
	}
	public int throwAwayClause(String[] clauseArr, String strKey){
		int idx1 = -2, idx2 = -2, idx3 = -2, idx4 = -2, idx5 = -2, idx6 = -2, idx7 = -2, idx8 = -2;
		//String strKey = analysisOfStats.getMonth() + "月";
		boolean throwAway = false;
		int index = -1;
		int score = 0;
		for(int k = 0; k < clauseArr.length; k++){
			idx1 = clauseArr[k].indexOf("预计");
			idx2 = clauseArr[k].indexOf("预测");
			idx3 = clauseArr[k].indexOf(strKey);	
			idx4 = clauseArr[k].indexOf("CPI");
			idx5 = clauseArr[k].indexOf("PPI");
			idx6 = clauseArr[k].indexOf("同比");
			idx7 = clauseArr[k].indexOf("环比");
			idx8 = clauseArr[k].indexOf("上旬");
			if(idx1 >= 0 || idx2 >= 0){
				if(idx1 < idx3 || idx2 < idx3){
					throwAway = true;
				}else{
					score = score + 1;
				}
			}
			if(idx3 >= 0){
				if(idx8 >= 0 && idx3 < idx8){
					
				}else if(idx3 < idx4 || idx3 < idx5 || idx3 < idx6 || idx3 < idx7){
					throwAway = true;
				}else{
					score = score + 1;
				}
			}
			if(idx4 >= 0 || idx5 >= 0){
				score = score + 1;
			}
			if(idx6 >= 0 || idx7 >= 0){					
				score = score + 1;
			}
			if (score >= 3){
				throwAway = true;
			}
			if(throwAway == true){
				index = k;
				return index;
			}
		}
        return -1;
	}

}
