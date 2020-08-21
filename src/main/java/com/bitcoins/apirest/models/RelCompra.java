package com.bitcoins.apirest.models;

import java.io.Serializable;

public class RelCompra implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float vlTotInvCompras;
	
	private float vlLucro;
	
	private float vlBitCoinDia;
	
	private float saldoAtualBitCoin;

	public float getVlTotInvCompras() {
		return vlTotInvCompras;
	}

	public void setVlTotInvCompras(float vlTotInvCompras) {
		this.vlTotInvCompras = vlTotInvCompras;
	}

	public float getVlLucro() {
		return vlLucro;
	}

	public void setVlLucro(float vlLucro) {
		this.vlLucro = vlLucro;
	}

	public float getVlBitCoinDia() {
		return vlBitCoinDia;
	}

	public void setVlBitCoinDia(float vlBitCoinDia) {
		this.vlBitCoinDia = vlBitCoinDia;
	}

	public float getSaldoAtualBitCoin() {
		return saldoAtualBitCoin;
	}

	public void setSaldoAtualBitCoin(float saldoAtualBitCoin) {
		this.saldoAtualBitCoin = saldoAtualBitCoin;
	}
		
	
}
