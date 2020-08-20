package com.bitcoins.apirest.controllers;

import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.bitcoins.apirest.exception.APIException;
import com.bitcoins.apirest.models.BitCoins;
import com.bitcoins.apirest.models.Clientes;
import com.bitcoins.apirest.models.CotacaoReal;
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
	
	//https://api.coinbase.com/v2/prices/spot?currency=BRL
	//https://www.youtube.com/watch?v=wvKuWmA-77E
	
	@GetMapping("/bitcoins")
	@ApiOperation(value="Obtem a Cotação do BitCoin.")
	public CotacaoReal cotacaoBitCoin(){
		RestTemplate template = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.coinbase.com")
				.path("v2/prices/spot")
				.queryParam("currency", "BRL")
				.build();
		
		CotacaoReal Teste = template.getForObject(uri.toUriString(), CotacaoReal.class);
		
		CotacaoReal cotacaoReal = new CotacaoReal();
		
		/*{"data":{"base":"BTC","currency":"BRL","amount":"65363.568785"}}*/
		cotacaoReal.setBase("BTC");
		cotacaoReal.setCurrency("BRL");
		cotacaoReal.setAmount(65363.568785f);
		
		return cotacaoReal;
	}

	
	@GetMapping("/bitcoins/{id_cliente}/{qtd_compra}")
	@ApiOperation(value="Realiza a Compra do BitCoin.")
	public List<BitCoins> compraBitCoin(@PathVariable(value = "id_cliente") long id_cliente,
									    @PathVariable(value = "qtd_compra") float qtd_compra) throws APIException{

		
		CotacaoReal cotacaoReal = new CotacaoReal();
		cotacaoReal.setAmount(15.00f);
		
		Clientes cliente = new Clientes();
		cliente = clienteRepository.findById(id_cliente); 
		
		if (cliente == null) {
			throw new APIException("Cliente não Localizado!");
		}			
		
		// verifica se o clientem tem o saldo suficiente.
		if((qtd_compra*cotacaoReal.getAmount()) > cliente.getSaldo()) {
			throw new APIException("Cliente não possuiu Saldo suficiente para a compra! Total:" + qtd_compra*cotacaoReal.getAmount());
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
