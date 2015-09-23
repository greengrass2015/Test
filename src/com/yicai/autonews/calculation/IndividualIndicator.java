package com.yicai.autonews.calculation;

public class IndividualIndicator {
	private double mom;
	private double yoy;
	private int month;
	private int year;
	public IndividualIndicator(){
	}
	public IndividualIndicator(double individualMom, double individualYoy, int month, int year){
		this.mom = individualMom;
		this.yoy = individualYoy;
		this.month = month;
		this.year = year;
	}
	public int getMonth(){
		return this.month;
	} 
	public void setMonth(int month){
		this.month = month;
	} 
	public int getYear(){
		return this.year;
	}
	public void setYear(int year){
		this.year = year;
	} 
	public double getMom(){
		return this.mom;
	}
	public void setMom(){
		this.mom = mom;
	}
	public double getYoy(){
		return this.yoy;
	}
	public void setYoy(){
		this.yoy = yoy;
	}
}
