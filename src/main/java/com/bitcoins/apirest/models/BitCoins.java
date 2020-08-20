package com.bitcoins.apirest.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="TB_CIET_BITCOINS")
public class BitCoins implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Clientes cliente;
	
	private OffsetDateTime dhCompra;
	
	private float vlPago;
	
	private float qtdBitCoin;
	
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public OffsetDateTime getDhCompra() {
		return dhCompra;
	}

	public void setDhCompra(OffsetDateTime dhCompra) {
		this.dhCompra = dhCompra;
	}

	public float getVlPago() {
		return vlPago;
	}

	public void setVlPago(float vlPago) {
		this.vlPago = vlPago;
	}

	public float getQtdBitCoin() {
		return qtdBitCoin;
	}

	public void setQtdBitCoin(float qtdBitCoin) {
		this.qtdBitCoin = qtdBitCoin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
}
