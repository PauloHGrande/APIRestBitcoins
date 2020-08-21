package com.bitcoins.apirest.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CotacaoReal2 implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String base;

	private String currency;
	
	private Float amount;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	
	
}
