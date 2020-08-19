package com.bitcoins.apirest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitcoins.apirest.exception.APIException;
import com.bitcoins.apirest.models.Clientes;
import com.bitcoins.apirest.repository.ClienteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/api")
@Api(value="Api Rest Clientes")
@CrossOrigin(origins = "*")
public class ClienteControllers {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping("/clientes")
	@ApiOperation(value="Retorna uma Lista de Clientes.")
	public List<Clientes> listaClientes(){
		// Busca todos os Clientes
		return clienteRepository.findAll();
	}

	@GetMapping("/cliente/{id}")
	@ApiOperation(value="Retorna um Cliente Filtro por ID.")
	public Clientes listaClienteID(@PathVariable(value = "id") long id) throws APIException{
		// Busca um Cliente por ID
		if (clienteRepository.findById(id) == null) {
			throw new APIException("Cliente não Localizado!");
		}	
		
		return clienteRepository.findById(id);
	}
	
	@PostMapping("/cliente")
	@ApiOperation(value="Salva um Cliente.")
	public Clientes salvarCliente(@RequestBody Clientes cliente) throws APIException{
		// Salva um Cliente no Banco de Dados.
		
		if (cliente.getNome().length() > 50) {
			throw new APIException("O Campo Nome não pode ter mais que 50 caracteres.");
		}
		if (cliente.getNome().length() > 14) {
			throw new APIException("O Campo Cpf não pode ter mais que 14 caracteres.");
		}
		
		try {						
			return clienteRepository.save(cliente);			
		} catch (Exception e) {
			throw new APIException("Cpf já Cadatrado!");
		}
		
	}
	
	@DeleteMapping("/cliente")
	@ApiOperation(value="Apaga um Cliente.")
	public void apagarCliente(@RequestBody Clientes cliente) throws APIException{
		// Apagar um Cliente no Banco de Dados.
		
		Clientes cli = new Clientes();
		
		if (cliente.getId() != 0) {
			cli = clienteRepository.findById(cliente.getId());
			
			if (cli == null) {
				throw new APIException("Cliente não Localizado!");
			}		
			
		}
		
		try {
			clienteRepository.delete(cli);
		} catch (Exception e) {
			throw new APIException("Erro ao tentar apagar o Cliente!");
		}
	}	
	
	@PatchMapping("/cliente")
	@ApiOperation(value="Atualiza um Cliente.")
	public Clientes atualizarCliente(@RequestBody Clientes cliente) throws APIException{
		// Atualizar um Cliente no Banco de Dados.
		try {
			return clienteRepository.save(cliente);
		} catch (Exception e) {
			throw new APIException("Erro ao tentar Atualizar o Cliente!");
		}
		
	}	
}
