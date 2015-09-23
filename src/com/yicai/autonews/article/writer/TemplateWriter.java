package com.yicai.autonews.article.writer;
import java.util.List;

import com.github.cyl.autonews.dao.analysis.AnalysisDao;
import com.github.cyl.autonews.dao.cpi.CPIDao;
import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.analysis.Clause;
import com.github.cyl.autonews.pojo.analysis.Paragraph;
import com.github.cyl.autonews.pojo.analysis.Sentence;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
import com.github.cyl.autonews.pojo.ppi.MonthPPI;
import com.yicai.autonews.staticclass.MyDate;
import com.yicai.autonews.calculation.IndividualIndicator;
import com.yicai.autonews.calculation.IndividualIndicatorComparison;
import com.yicai.autonews.calculation.IndividualIndicatorYoyComparator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TemplateWriter {
	//private AnalysisDao analysisDao = new AnalysisDao();
	//private CPIDao cpiDao = new CPIDao();
	//private Article article;
	private List<MonthCPI> historicalMonthCPIList; 
	private List<MonthPPI> historicalMonthPPIList; 
	private MonthCPI curMonthCPI; 
	private MonthPPI curMonthPPI;
	final int yearToMonth = 12;
	public TemplateWriter(){
	}
	public TemplateWriter(List<MonthCPI> historicalMonthCPIList, List<MonthPPI> historicalMonthPPIList,
			MonthCPI curMonthCPI, MonthPPI curMonthPPI){
		this.historicalMonthCPIList = historicalMonthCPIList;
		this.historicalMonthPPIList = historicalMonthPPIList;
		this.curMonthCPI = curMonthCPI;
		this.curMonthPPI = curMonthPPI;
	}
	
	public String articleTitleGenerator(){

		String title = "";
		// 如果有至少一个月 的历史数据，就和历史数据比比，如果没有，不做比较
		if (historicalMonthCPIList.size() >= 1){ 
	       MonthCPI lastMonthCPI = historicalMonthCPIList.get(historicalMonthCPIList.size()-1);
	        
	        if(curMonthCPI.getYoy() >= 2 && curMonthCPI.getYoy() <3){
	        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
	        		if (lastMonthCPI.getYoy() < 2){
	        			title = "CPI重新站上2时代"; //2 加""
	        		}
	        	}else{
	        		title = "CPI涨幅回落";
	        	}
	        }else if(curMonthCPI.getYoy() < 2 && curMonthCPI.getYoy() >= 1){
	        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
	        		if (lastMonthCPI.getYoy() > 0){
	        			title = "物价持续温和上涨"; 
	        		}
	        	}else{
	        		title = "CPI涨幅回落";
	        	}
	        }else if(curMonthCPI.getYoy() <= 0){
	        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
	        		title = "物价降幅收窄"; 
	        	}else{
	        		title = "物价持续下行"; 
	        	}
	        }else if(curMonthCPI.getYoy() < 1 && curMonthCPI.getYoy() > 0){
	        	title = String.format("CPI同比仅%s增加通缩担忧", trendOfVariation(curMonthCPI.getYoy()));	
	        }else{
	        	title = String.format("CPI同比%s", trendOfVariation(curMonthCPI.getYoy()));	
	        }
		}else{
			if(curMonthCPI.getYoy() < 1){
				title = String.format("CPI同比%s 增加通缩担忧", trendOfVariation(curMonthCPI.getYoy()));
			}else if(curMonthCPI.getYoy() < 2){
				title = String.format("CPI同比%s 物价温和上涨", trendOfVariation(curMonthCPI.getYoy()));
			}else{
				title = String.format("CPI同比%s", trendOfVariation(curMonthCPI.getYoy()));
			}
			
		}

		return title;
	}
	
	public Paragraph firstParagraphGenerator(){
		//historicalM;
        Paragraph firstParagraph = new Paragraph();
        List<Sentence> sentenceList = new ArrayList<Sentence>(); 
       
       String str = "";
       if(historicalMonthCPIList.size() >= 1){
    	   MonthCPI lastMonthCPI = historicalMonthCPIList.get(historicalMonthCPIList.size()-1);
        if(curMonthCPI.getYoy() >= 2 && curMonthCPI.getYoy() <3){
        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
        		if (lastMonthCPI.getYoy() < 2){
        			str = String.format("根据国家统计局公布的数据显示，%d月份居民消费价格指数CPI同比%s，比上月扩大%s个百分点，温和通胀趋势显现。",
        					curMonthCPI.getMonth(),trendOfVariation(curMonthCPI.getYoy()),formatVariation(curMonthCPI.getYoy()-lastMonthCPI.getYoy()));
        		}
        	}else{
        		str = String.format("根据国家统计局公布的数据显示，%d月份居民消费价格指数CPI同比%s，比上月收窄%s个百分点，涨幅回落。",
        				curMonthCPI.getMonth(),trendOfVariation(curMonthCPI.getYoy()),formatVariation(curMonthCPI.getYoy()-lastMonthCPI.getYoy()));
        	}
        }else if(curMonthCPI.getYoy() <= 1 && curMonthCPI.getYoy() > 0){
	        str = String.format("根据国家统计局公布的数据显示，%d月份居民消费价格指数CPI同比%s，环比%s；%d月份的PPI同比%s，环比%s。",
	        		curMonthCPI.getMonth(),trendOfVariation(curMonthCPI.getYoy()),trendOfVariation(curMonthCPI.getMom()),
	        		curMonthCPI.getMonth(),trendOfVariation(curMonthPPI.getYoy()),trendOfVariation(curMonthPPI.getMom())
	                );
        }else{
	        str = String.format("根据国家统计局公布的数据显示，%d月份居民消费价格指数CPI同比%s，环比%s；%d月份的PPI同比%s，环比%s。",
	        		curMonthCPI.getMonth(),trendOfVariation(curMonthCPI.getYoy()),trendOfVariation(curMonthCPI.getMom()),
	        		curMonthCPI.getMonth(),trendOfVariation(curMonthPPI.getYoy()),trendOfVariation(curMonthPPI.getMom())
	                );
        }
        }else{
        	 str = String.format("根据国家统计局公布的数据显示，%d月份居民消费价格指数CPI同比%s，环比%s；%d月份的PPI同比%s，环比%s。",
 	        		curMonthCPI.getMonth(),trendOfVariation(curMonthCPI.getYoy()),trendOfVariation(curMonthCPI.getMom()),
 	        		curMonthCPI.getMonth(),trendOfVariation(curMonthPPI.getYoy()),trendOfVariation(curMonthPPI.getMom())
 	                );
        }
        Sentence sentenceTemp = new Sentence(str);
        sentenceTemp.setType(0); // 自主产生
        sentenceList.add(sentenceTemp);
       
        int nLargestMonth = new IndividualIndicatorComparison().numOfMonthCurrentIndicatorYoyBiggerThanPast(curMonthCPI, historicalMonthCPIList);
        int nContinousMonth = new IndividualIndicatorComparison().numOfMonthContinousIncrease(curMonthCPI, historicalMonthCPIList);
        System.out.println("创" + nLargestMonth + "月的新高");
        System.out.println("连续" + nContinousMonth + "月持续增加");
        
       // if (nLargestMonth >=  )


        
        firstParagraph.setSentences(sentenceList);
        //List<MonthCPI> historicalMonthCPIListCopy = new ArrayList<MonthCPI>();
    	//Collections.copy(historicalMonthCPIListCopy, historicalMonthCPIList);

		
		return firstParagraph;
	}
	
	public String cpiTitleGenerator(){
		String cpiTitle = "";
		//double cpiMomCurrent = curMonthCPI.getMom();
		double cpiYoyCurrent = curMonthCPI.getYoy();
		if(historicalMonthCPIList.size() >= 1){
			int idxLastMonth = historicalMonthCPIList.size()-1;
		//double cpiMomLastMonth = historyMonthCPIList.get(idxLastMonth).getMom();
			double cpiYoyLastMonth = historicalMonthCPIList.get(idxLastMonth).getYoy();
			cpiTitle = subTitleGenerator("居民消费价格(CPI)同比",cpiYoyCurrent, cpiYoyLastMonth);
        }
		else{
			cpiTitle = "居民消费价格CPI同比" + trendOfVariation(curMonthCPI.getYoy());
		}
		return cpiTitle;
	}
	
	public String ppiTitleGenerator(){
		String ppiTitle = "";
		//double ppiMomCurrent = curMonthPPI.getMom();
		double ppiYoyCurrent = curMonthPPI.getYoy();
		if (historicalMonthCPIList.size() >= 1){
			int idxLastMonth = historicalMonthPPIList.size()-1;
			//double ppiMomLastMonth = historyMonthPPIList.get(idxLastMonth).getMom();
			double ppiYoyLastMonth = historicalMonthPPIList.get(idxLastMonth).getYoy();
			ppiTitle = subTitleGenerator("PPI同比",ppiYoyCurrent, ppiYoyLastMonth);
		}else{
			ppiTitle = "工业生产者出厂价格PPI" + trendOfVariation(curMonthPPI.getYoy());
		}
		return ppiTitle;
	}
		
	public String subTitleGenerator(String indexType, double curIndex, double lastMonthIndex){
		String subTitle;
		if (curIndex > 0){
			if(curIndex > lastMonthIndex){
				if(lastMonthIndex>0){
					subTitle  = indexType + "涨幅持续扩大";
				}else if(lastMonthIndex==0){
					subTitle  = indexType + "涨幅扩大";
				}else{
					subTitle  = indexType + "涨幅由降转升";
				}
			}else if(curIndex == lastMonthIndex){
				subTitle  = indexType + "涨幅与上月持平";
			}else{
				subTitle  = indexType + "涨幅回落";
			}
			
		} else if(curIndex == 0){
			if(curIndex > lastMonthIndex){
				subTitle  = indexType + "降幅收窄";
			}else if(curIndex == lastMonthIndex){
				subTitle  = indexType + "与上月持平";
			}else{
				subTitle  = indexType + "涨幅回落";
			}
		}else{
			if(curIndex > lastMonthIndex){
				subTitle  = indexType + "降幅收窄";
			}else if(curIndex == lastMonthIndex){
				subTitle  = indexType + "与上月持平";
			}else{
				subTitle  = indexType + "降幅扩大";
			}	
		}
		return subTitle;
	}
	
	

	public String formatVariation(double percentage ){
		DecimalFormat df  = new DecimalFormat("##0.0");
		return df.format(Math.abs(percentage));
	}
	public String trendOfVariation(double percentage ){
		String str;
		DecimalFormat df  = new DecimalFormat("##0.0");
		if (percentage > 0){
			str = "增加" + df.format(Math.abs(percentage)) + "%";
			//str = "增加" ;
		} else if(percentage < 0){
			str = "减少" + df.format(Math.abs(percentage)) + "%";	
		//	str = "减少";	
		} else{
			str = "持平";
		}
		return str;
	}
	
}
