package com.bitcoins.apirest.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CotacaoReal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CotacaoReal2 data;

	public CotacaoReal2 getData() {
		return data;
	}

	public void setData(CotacaoReal2 data) {
		this.data = data;
	}
	
	
}
