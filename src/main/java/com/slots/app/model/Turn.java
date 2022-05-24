package com.slots.app.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Turn {
	@Id
	private String turnId;
	
	private Date creationDate = new Date();
	
	private Double amountBet;
	
	private String symbol1;
	private String symbol2;
	private String symbol3;
	
	
	public String getTurnId() {
		return turnId;
	}
	public void setTurnId(String turnId) {
		this.turnId = turnId;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getAmountBet() {
		return amountBet;
	}
	public void setAmountBet(Double amountBet) {
		this.amountBet = amountBet;
	}

	public String getSymbol1() {
		return symbol1;
	}
	public void setSymbol1(String symbol1) {
		this.symbol1 = symbol1;
	}

	public String getSymbol2() {
		return symbol2;
	}
	public void setSymbol2(String symbol2) {
		this.symbol2 = symbol2;
	}

	public String getSymbol3() {
		return symbol3;
	}
	public void setSymbol3(String symbol3) {
		this.symbol3 = symbol3;
	}
}
