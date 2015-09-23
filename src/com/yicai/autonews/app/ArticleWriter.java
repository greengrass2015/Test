package com.yicai.autonews.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.cyl.autonews.dao.analysis.AnalysisDao;
import com.github.cyl.autonews.dao.cpi.*;
import com.github.cyl.autonews.dao.ppi.PPIDao;
import com.github.cyl.autonews.dao.quotation.QuotationDao;
import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Section;
import com.github.cyl.autonews.pojo.analysis.Sentence;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
import com.github.cyl.autonews.pojo.ppi.MonthPPI;
import com.yicai.autonews.staticclass.MyDate;
import com.yicai.autonews.article.assemble.ArticleAssemble;
import com.yicai.autonews.article.writer.*;
import com.yicai.autonews.articlegenerator.ArticleGenerator;
import com.yicai.autonews.calculation.IndividualIndicatorYoyComparator;
import com.yicai.autonews.cvsfile.CsvFileExport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class ArticleWriter {
	public static void main(String[] args) {
		int year = 2015;
		int month = 4;
		ArticleGenerator articleGenerator = new ArticleGenerator();
		articleGenerator.newsArticle(year, month);
		//sentence Type: 1   余秋梅专家解读   2 经济学家评论  0：自主产生
		
		
	}

}
