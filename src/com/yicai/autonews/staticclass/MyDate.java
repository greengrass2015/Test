package com.yicai.autonews.staticclass;

public class MyDate {
	
	public static int year;
	public static int month;
	public MyDate(){
	}
	public MyDate(int year, int month){
		MyDate.year = year;
		MyDate.month = month;
	}
	public void setYear(int year){
		MyDate.year = year; 
	}
	public int getYear(){
		return MyDate.year;
	}
	public void setMonth(int month){
		MyDate.month = month; 
	}
	public int getMonth(){
		return MyDate.month;
	}
}
