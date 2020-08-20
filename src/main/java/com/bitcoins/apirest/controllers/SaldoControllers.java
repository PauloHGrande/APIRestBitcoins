package com.bitcoins.apirest.controllers;

import java.time.OffsetDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitcoins.apirest.exception.APIException;
import com.bitcoins.apirest.models.Clientes;
import com.bitcoins.apirest.models.Saldos;
import com.bitcoins.apirest.repository.ClienteRepository;
import com.bitcoins.apirest.repository.SaldoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/api")
@Api(value="Api Rest Lançamentos de Saldos")
@CrossOrigin(origins = "*")
public class SaldoControllers {
	
	@Autowired
	SaldoRepository saldoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@PostMapping("/saldo/{id_cliente}/{valor}")
	@ApiOperation(value="Faz o Lançamento de Saldo do Cliente.")
	public Saldos lançamento(@PathVariable(value = "id_cliente") long id_cliente, @PathVariable(value = "valor") Float valor) throws APIException{

		//Buscar o Cliente.
		Clientes cliente = clienteRepository.findById(id_cliente);
		if(cliente == null) {
			throw new APIException("Cliente não Localizado!");
		}
		if(valor <= 0.f) {
			throw new APIException("Valor não pode ser Menor ou Igual a Zero!");
		}
		
		Saldos saldo = new Saldos();
		saldo.setCliente(cliente);
		saldo.setDataHora(OffsetDateTime.now());
		saldo.setValor(valor);
		
		cliente.setSaldo(cliente.getSaldo() + valor);
		
		try {						
			 saldoRepository.save(saldo);
			 clienteRepository.save(cliente);
		} catch (Exception e) {
			throw new APIException("Erro ao Lançar o Saldo!");
		}
		
		return saldo;
	}
	
	@GetMapping("/saldo/{id_cliente}")
	@ApiOperation(value="Busca historico de saldo do Cliente.")
	public List<Saldos> buscaSaldoCliente(@PathVariable(value = "id_cliente") long id_cliente) throws APIException{

		//Buscar o Cliente.
		Clientes cliente = clienteRepository.findById(id_cliente);
		if(cliente == null) {
			throw new APIException("Cliente não Localizado!");
		}
		
		
		return saldoRepository.findSaldoCliente(id_cliente);
	}
	
	@DeleteMapping("/saldo/{id}")
	@ApiOperation(value="Limpa a Tabela Saldo.")
	public void apagarCliente() throws APIException{
		// Apagar o Saldo de todos os Clientes.
				
		List<Saldos> saldos = saldoRepository.findAll();
				
		try {
			
			for (Saldos item : saldos) {
				saldoRepository.delete(item);
			}
			
		} catch (Exception e) {
			throw new APIException("Erro ao tentar apagar o Saldo!");
		}
	}		
	
	
}
