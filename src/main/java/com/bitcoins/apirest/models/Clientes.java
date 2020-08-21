package com.bitcoins.apirest.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="TB_CIET_CLIENTE", uniqueConstraints={@UniqueConstraint(columnNames={"cpf"})})
public class Clientes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//@Size(max = 50, message = "O Nome não pode ter mais que 50 caracteres.")
	private String nome;
	
	//@Size(max = 14, message = "O Cpf não pode ter mais que 14 caracteres.")
	private String cpf;
	
	private Float saldo = 0.f;
	
	
	public Float getSaldo() {
		return saldo;
	}
	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
}
