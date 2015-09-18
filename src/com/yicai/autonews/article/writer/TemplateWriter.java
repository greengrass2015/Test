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
import com.yicai.autonews.calculation.IndicatorYoyComparator;

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
		//int year = 2015; 
		//int month = 5;
		String title = null;
		/*List<String> titleTemplate;
		titleTemplate.add();*/
	       MonthCPI lastMonthCPI = historicalMonthCPIList.get(historicalMonthCPIList.size()-1);
	        
	        if(curMonthCPI.getYoy() >= 2 && curMonthCPI.getYoy() <3){
	        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
	        		if (lastMonthCPI.getYoy() < 2){
	        			title = "CPI重新站上2时代"; //2 加""
	        		}
	        	}else{
	        		title = "CPI涨幅回落";
	        	}
	        }else if(curMonthCPI.getYoy() < 2 && curMonthCPI.getYoy() > 0){
	        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
	        		if (lastMonthCPI.getYoy() > 2){
	        			title = "物价持续温和上涨"; //2 加""
	        		}
	        	}else{
	        		title = "CPI涨幅回落";
	        	}
	        }else if(curMonthCPI.getYoy() <= 0){
	        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
	        		title = "物价降幅收窄"; //2 加""
	        	}else{
	        		title = "物价持续下行"; //2 加""
	        	}
	        }else{
	        	
	        }

		return title;
	}
	
	public Paragraph firstParagraphGenerator(){
		//historicalM;
        Paragraph firstParagraph = new Paragraph();
        List<Sentence> sentenceList = new ArrayList<Sentence>(); 
       MonthCPI lastMonthCPI = historicalMonthCPIList.get(historicalMonthCPIList.size()-1);
       String str = null;
        
        if(curMonthCPI.getYoy() >= 2 && curMonthCPI.getYoy() <3){
        	if(curMonthCPI.getYoy() > lastMonthCPI.getYoy()){
        		if (lastMonthCPI.getYoy() < 2){
        			str = String.format("根据国家统计局公布的数据显示,%d月份居民消费价格指数CPI同比%s,比上月扩大%s个百分点,温和通胀趋势显现。",
        	                MyDate.month,trendOfVariation(curMonthCPI.getYoy()),formatVariation(curMonthCPI.getYoy()-lastMonthCPI.getYoy()));
        		}
        	}else{
        		str = String.format("根据国家统计局公布的数据显示,%d月份居民消费价格指数CPI同比%s,比上月收窄%s个百分点,涨幅回落。",
    	                MyDate.month,trendOfVariation(curMonthCPI.getYoy()),formatVariation(curMonthCPI.getYoy()-lastMonthCPI.getYoy()));
        	}
        }else{
	        str = String.format("根据国家统计局公布的数据显示,%d月份居民消费价格指数CPI同比%s,环比%s;%d月份的PPI同比%s,环比%s\n",
	                MyDate.month,trendOfVariation(curMonthCPI.getYoy()),trendOfVariation(curMonthCPI.getMom()),
	                MyDate.month,trendOfVariation(curMonthPPI.getYoy()),trendOfVariation(curMonthPPI.getMom())
	                );
        }
        Sentence sentenceTemp = new Sentence(str);
        sentenceList.add(sentenceTemp);
        firstParagraph.setSentences(sentenceList);
        //List<MonthCPI> historicalMonthCPIListCopy = new ArrayList<MonthCPI>();
    	//Collections.copy(historicalMonthCPIListCopy, historicalMonthCPIList);
        /*
		for(int i = 0; i < MyDate.month - 1; i++){
			System.out.println("排序前"+i+"月的CPI同比是:" + historicalMonthCPIList.get(i).getYoy()+"\n");
		}
		
		//Collections.sort(historicalMonthCPIList, new IndicatorYoyComparator());
		
		for(int i = 0; i < MyDate.month - 1; i++){
			System.out.println("未排序的"+historicalMonthCPIList.get(i).getMonth() +
					"月的CPI同比是:"+historicalMonthCPIList.get(i).getYoy() + "\n");
		}
		for(int i = 0; i < MyDate.month - 1; i++){
			System.out.println("排序后"+historicalMonthCPIListCopy.get(i).getMonth() +
					"月的CPI同比是:"+historicalMonthCPIListCopy.get(i).getYoy() + "\n");
		}*/
		
		return firstParagraph;
	}
	
	public String cpiTitleGenerator(){
		String cpiTitle = null;
		//double cpiMomCurrent = curMonthCPI.getMom();
		double cpiYoyCurrent = curMonthCPI.getYoy();
		int idxLastMonth = historicalMonthCPIList.size()-1;
		//double cpiMomLastMonth = historyMonthCPIList.get(idxLastMonth).getMom();
		double cpiYoyLastMonth = historicalMonthCPIList.get(idxLastMonth).getYoy();
        cpiTitle = subTitleGenerator("居民消费价格(CPI)同比",cpiYoyCurrent, cpiYoyLastMonth);
		return cpiTitle;
	}
	
	public String ppiTitleGenerator(){
		String ppiTitle = null;
		//double ppiMomCurrent = curMonthPPI.getMom();
		double ppiYoyCurrent = curMonthPPI.getYoy();
		int idxLastMonth = historicalMonthPPIList.size()-1;
		//double ppiMomLastMonth = historyMonthPPIList.get(idxLastMonth).getMom();
		double ppiYoyLastMonth = historicalMonthPPIList.get(idxLastMonth).getYoy();
        ppiTitle = subTitleGenerator("PPI同比",ppiYoyCurrent, ppiYoyLastMonth);
		return ppiTitle;
	}
	
	public String titleGenerator(double curIndex, double lastMonthIndex){
		String title = null;
		/*if (curIndex > 0){
			if(curIndex > lastMonthIndex){
				if(lastMonthIndex > 0){
					//title  = "CPI涨幅持续扩大";
					if(curIndex < 2){
						String [] titleList= {"物价涨幅持续温和","CPI涨幅持续扩大","CPI持续温和上涨"};
					}else(curIndex < 3){
						String [] titleList= {"物价涨幅持续温和"};
					}else{
						
					}
					
				}else if(lastMonthIndex==0){
					title  = "物价上涨";
				}else{
					title  = "涨幅由降转升";
				}
			}else if(curIndex == lastMonthIndex){
				title  = "涨幅与上月持平";
			}else{
				title  = "涨幅回落";
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
		}*/
		return title;
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
