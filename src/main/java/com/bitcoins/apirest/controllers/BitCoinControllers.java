package com.bitcoins.apirest.controllers;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bitcoins.apirest.exception.APIException;
import com.bitcoins.apirest.models.BitCoins;
import com.bitcoins.apirest.models.Clientes;
import com.bitcoins.apirest.models.CotacaoReal2;
import com.bitcoins.apirest.repository.BitCoinRepository;
import com.bitcoins.apirest.repository.ClienteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/api")
@Api(value="Api Rest Compra de BitCoins")
@CrossOrigin(origins = "*")
public class BitCoinControllers {
	
	@Autowired
	BitCoinRepository bitCoinRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	RelatoriosControllers relatoriosControllers;
	
	@GetMapping("/bitcoins")
	@ApiOperation(value="Obtem a Cotação do BitCoin.")
	public CotacaoReal2 cotacaoBitCoin(){
		
		// Busca a Cotação pelo consumo da API.
		return relatoriosControllers.cotacaoBitCoin();
	}

	
	@GetMapping("/bitcoins/{id_cliente}/{qtd_compra}")
	@ApiOperation(value="Realiza a Compra do BitCoin.")
	public List<BitCoins> compraBitCoin(@PathVariable(value = "id_cliente") long id_cliente,
									    @PathVariable(value = "qtd_compra") float qtd_compra) throws APIException{

		
		// Busca a Cotação pelo consumo da API.
		CotacaoReal2 buscaCotacao = relatoriosControllers.cotacaoBitCoin();
		
		CotacaoReal2 cotacaoReal = new CotacaoReal2();
		cotacaoReal.setAmount(buscaCotacao.getAmount());
		
		Clientes cliente = new Clientes();
		cliente = clienteRepository.findById(id_cliente); 
		
		if (cliente == null) {
			throw new APIException("Cliente não Localizado!");
		}			
		
		// verifica se o clientem tem o saldo suficiente.
		if((qtd_compra * cotacaoReal.getAmount()) > cliente.getSaldo()) {
			throw new APIException("Cliente não possuiu Saldo suficiente para a compra! Total:" + qtd_compra * cotacaoReal.getAmount());
		}
		
		BitCoins bc = new BitCoins();
		bc.setCliente(cliente);
		bc.setDhCompra(OffsetDateTime.now());
		bc.setQtdBitCoin(qtd_compra);
		bc.setStatus("Sucesso");
		bc.setVlPago(qtd_compra * cotacaoReal.getAmount());
		
		cliente.setSaldo(cliente.getSaldo() - (qtd_compra * cotacaoReal.getAmount()));
		
		try {						
			bitCoinRepository.save(bc);
			clienteRepository.save(cliente);
		} catch (Exception e) {
			throw new APIException("Erro ao Efetuar a Compra!");
		}
		
		return bitCoinRepository.findCompraCliente(id_cliente);
	}	
	
}
