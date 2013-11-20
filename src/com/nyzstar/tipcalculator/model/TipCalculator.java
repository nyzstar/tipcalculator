package com.nyzstar.tipcalculator.model;

public class TipCalculator{
	private double dbAmount = 0;
	private double dbTipPercent = 0;
	private double dbTipAmount = 0;
	private double dbTotalAmount = 0;
	private int intPeopleNum = 1;
	private double dbAvgAmount = 0;
	
	public TipCalculator(double dbAmount, double dbTipPercent, int intPeopleNum){
		this.dbAmount = dbAmount;
		this.dbTipPercent = dbTipPercent;
		this.intPeopleNum = intPeopleNum;
		this.dbTipAmount = this.dbAmount * this.dbTipPercent/100;
		this.dbTotalAmount = this.dbAmount + this.dbTipAmount;
		// need to add exception this.
		this.dbAvgAmount = this.dbTotalAmount/this.intPeopleNum;
	}
	
	//-----------------------------------------------------------------
	//getter for amount
	//-----------------------------------------------------------------
	public double getAmount(){
		return dbAmount;
	}
	//-----------------------------------------------------------------
	//setter for amount
	//-----------------------------------------------------------------
	public void setAmount(double dbAmount){
		this.dbAmount = dbAmount;
		computeTipAmount();
		this.dbTotalAmount = this.dbAmount+this.dbTipAmount;
		computeAvgAmount();
	}
	//-----------------------------------------------------------------
	//getter for tip percent.
	//-----------------------------------------------------------------
	public double getTipPercent(){
		return dbTipPercent;
	}
	
	//-----------------------------------------------------------------
	//setter for tip percent
	//-----------------------------------------------------------------
	public void setTipPercent(double dbTipPercent){
		this.dbTipPercent = dbTipPercent;
		computeTipAmount();
		this.dbTotalAmount = this.dbTipAmount + this.dbAmount;
		computeAvgAmount();
	}
	
	//-----------------------------------------------------------------
	//getter for tip amount
	//-----------------------------------------------------------------
	public double getTipAmount(){
		return dbTipAmount;
	}
	
	//-----------------------------------------------------------------
	//setter for tip amount
	//-----------------------------------------------------------------
	public void setTipAmount(double dbTipAmount){
		this.dbTipAmount = dbTipAmount;
		computeTipPercent();
		this.dbTotalAmount = this.dbTipAmount + this.dbAmount;
		computeAvgAmount();	
	}
	//-----------------------------------------------------------------
	//getter for total amount
	//-----------------------------------------------------------------
	public double getTotalAmount(){
		return this.dbTotalAmount;
	}
	
	//-----------------------------------------------------------------
	//setter for total amount
	//-----------------------------------------------------------------
	public void setTotalAmount(double dbTotalAmount){
		this.dbTotalAmount = dbTotalAmount;
		this.dbTipAmount = this.dbTotalAmount - this.dbAmount;
		computeTipPercent();
		computeAvgAmount();
	}
	
	//-----------------------------------------------------------------
	//getter for intPeopleNum
	//-----------------------------------------------------------------
	public int getPeopleNum(){
		return this.intPeopleNum;
	}
	
	//-----------------------------------------------------------------
	//setter for intPeopleNum
	public void setPeopleNum(int intPeopleNum){
		this.intPeopleNum = intPeopleNum;
		computeAvgAmount();
	}
	
	//-----------------------------------------------------------------
	//getter for dbAvgAmount
	//-----------------------------------------------------------------
	public double getAvgAmount(){
		return this.dbAvgAmount;
	}
	
	//-----------------------------------------------------------------
	//compute average amount
	//-----------------------------------------------------------------
	private void computeAvgAmount(){
		this.dbAvgAmount = this.dbTotalAmount/this.intPeopleNum;
	}
	
	//-----------------------------------------------------------------
	//compute tip amount
	//-----------------------------------------------------------------
	private void computeTipAmount(){
		this.dbTipAmount = this.dbAmount*this.dbTipPercent/100;
	}
	
	//-----------------------------------------------------------------
	//compute tip percent
	//-----------------------------------------------------------------
	private void computeTipPercent(){
		this.dbTipPercent = this.dbTipAmount*100/this.dbAmount;
	}
}
