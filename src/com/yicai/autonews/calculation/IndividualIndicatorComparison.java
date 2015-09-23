package com.yicai.autonews.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
import com.github.cyl.autonews.pojo.indicator.Indicator;

public class IndividualIndicatorComparison {
	/*final int yearToMonth = 12;
	public  List<IndividualIndicator> cpiSorting(List<MonthCPI> historicalMonthCPI){
		List<IndividualIndicator> historicalCPIList = new ArrayList<IndividualIndicator>() ;
		 
		int nsize = historicalMonthCPI.size(); 
		
		//List<Indicator> cpiIndicatorList = new ArrayList<Indicator>() ;
		for (int i = 0; i < nsize; i++){
			MonthCPI monthCPI = historicalMonthCPI.get(i);
			IndividualIndicator cpiIndicator = new IndividualIndicator(monthCPI.getMom(), monthCPI.getYoy(), monthCPI.getMonth(), monthCPI.getYear());
			historicalCPIList.add(cpiIndicator) ;
		}
		Collections.sort(historicalCPIList, new IndividualIndicatorYoyComparator());
		return historicalCPIList;
	}*/

	//public int numOfMonthCurrentIndicatorYoyBiggerThanPast( double curIndicatorYoy, List<IndividualIndicator> historicalIndicatorList){
	public int numOfMonthCurrentIndicatorYoyBiggerThanPast( MonthCPI curMonthCPI, List<MonthCPI> historicalIndicatorList){
		int  nMonth = 0;
		int latestSameMonth = 0;
		int latestBiggerMonth = 0;
		 List<Double> comparedResultList = new ArrayList<Double>();
		 for(int i = 0; i < historicalIndicatorList.size(); i++){
			 if (historicalIndicatorList.get(i).getYoy() > curMonthCPI.getYoy()){
				 comparedResultList.add(historicalIndicatorList.get(i).getYoy()); // 记下比当前月份大 的数值 

			 }else if (historicalIndicatorList.get(i).getYoy() == curMonthCPI.getYoy()){ //如果 相等，存入特殊值 1000.0做标记 
				 comparedResultList.add((Double) 1000.0);
			 }else{
				 comparedResultList.add(curMonthCPI.getYoy());
			 }
		 }
		 latestSameMonth = latestSameAsCurrentCPIYoy( historicalIndicatorList,  comparedResultList);
		 latestBiggerMonth = latestBiggerThanCurrentCPIYoy(curMonthCPI, historicalIndicatorList, comparedResultList);
		 if(latestBiggerMonth < latestSameMonth){
			 nMonth = latestBiggerMonth;
		 }else{
			 nMonth = latestSameMonth;
		 }
		 
		 
         return  nMonth;
	}
	//public int latestSameAsCurrentCPIYoy(List<IndividualIndicator> historicalIndicatorList,  List<Double> comparedResultList)
	public int latestSameAsCurrentCPIYoy(List<MonthCPI> historicalIndicatorList,  List<Double> comparedResultList){
		int nMonth = 0;
		for(int i = historicalIndicatorList.size() - 1; i >= 0; i--){
			nMonth = nMonth + 1;	
			 if( comparedResultList.get(i) == 1000.0){
					
				 return nMonth;
				 
			 }
		     	 
		 }
		 return nMonth+1;
	}
	//public int latestBiggerThanCurrentCPIYoy(double curIndicatorYoy, List<IndividualIndicator> historicalIndicatorList,  List<Double> comparedResultList)
	public int latestBiggerThanCurrentCPIYoy(MonthCPI curMonthCPI, List<MonthCPI> historicalIndicatorList,  List<Double> comparedResultList){
		int nMonth = 0;
		for(int i = historicalIndicatorList.size() - 1; i >= 0; i--){
			 nMonth = nMonth + 1;
			 if( comparedResultList.get(i) > curMonthCPI.getYoy()){
					return nMonth;			 
			 }
		    		 
		 }
		 return nMonth+1;
	}
	//public int numOfMonthContinousIncrease( double curIndicatorYoy, List<IndividualIndicator> historicalIndicatorList)
	public int numOfMonthContinousIncrease(MonthCPI curMonthCPI, List<MonthCPI> historicalIndicatorList){
		int  nMonth = 0;
		int latestSameMonth = 0;
		int latestBiggerMonth = 0;
		Double temp = 0.0;
		 List<Double> diffOfTwoConsecutiveMonth = new ArrayList<Double>();
		 for(int i = 0; i < historicalIndicatorList.size() - 1; i++){
			 temp = historicalIndicatorList.get(i+1).getYoy() - historicalIndicatorList.get(i).getYoy();
			 diffOfTwoConsecutiveMonth.add(temp);
		 }
		 //当前和上个月的差值
		 temp = curMonthCPI.getYoy() - historicalIndicatorList.get(historicalIndicatorList.size()-1).getYoy();
		 diffOfTwoConsecutiveMonth.add(temp);
         for(int i = diffOfTwoConsecutiveMonth.size()-1; i >= 0; i--){
        	 if(diffOfTwoConsecutiveMonth.get(i) > 0){
        		 nMonth = nMonth + 1;
        	 }else{
        		 return  nMonth;
        	 }
         }
		 
         return  nMonth;
	}


}
