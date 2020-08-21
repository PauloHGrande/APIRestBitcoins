package com.bitcoins.apirest.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.bitcoins.apirest.exception.APIException;
import com.bitcoins.apirest.models.BitCoins;
import com.bitcoins.apirest.models.Clientes;
import com.bitcoins.apirest.models.CotacaoReal;
import com.bitcoins.apirest.models.CotacaoReal2;
import com.bitcoins.apirest.models.RelCompra;
import com.bitcoins.apirest.repository.BitCoinRepository;
import com.bitcoins.apirest.repository.ClienteRepository;
import com.bitcoins.apirest.repository.SaldoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/api")
@Api(value="Api Rest Relatorios")
@CrossOrigin(origins = "*")
public class RelatoriosControllers {
	
	@Autowired
	BitCoinRepository bitCoinRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	SaldoRepository saldoRepository;
	
	
	@GetMapping("/relatorios/saldo/{id_cliente}")
	@ApiOperation(value="Retorna o Saldo Atual do Cliente.")
	public List<Clientes> saldoAtualCliente(@PathVariable(value = "id_cliente") long id_cliente) throws APIException{
		// Busca um Cliente por ID
		if (clienteRepository.findById(id_cliente) == null) {
			throw new APIException("Cliente não Localizado!");
		}	
		
		return clienteRepository.findSaldoCliente(id_cliente);
	}
	
	@GetMapping("/relatorios/bitcoins")
	@ApiOperation(value="Obtem a Cotação do BitCoin Real.")
	public CotacaoReal2 cotacaoBitCoin(){

		RestTemplate template = new RestTemplate();
		CotacaoReal2 cotacaoReal = new CotacaoReal2();

		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
	
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.coinbase.com")
				.path("v2/prices/spot")
				.queryParam("currency", "BRL")
				.build();
        
		ResponseEntity<CotacaoReal> Teste = template.exchange(uri.toString(), HttpMethod.GET, entity, CotacaoReal.class);

		cotacaoReal.setBase(Teste.getBody().getData().getBase());
		cotacaoReal.setCurrency(Teste.getBody().getData().getCurrency());
		cotacaoReal.setAmount(Teste.getBody().getData().getAmount());
		
		return cotacaoReal;
	}	
	
	@GetMapping("/relatorios/totalinvestido/{id_cliente}")
	@ApiOperation(value="Valor BitCoin-Dia/Lucro/Investimento e Saldo Atual por Cliente.")
	public RelCompra valorTotalInvestido(@PathVariable(value = "id_cliente") long id_cliente) throws APIException{
		
		// Busca a Cotação pelo consumo da API.
		CotacaoReal2 buscaCotacao = cotacaoBitCoin();
				
		RelCompra relcompra = new RelCompra();
		
		if (clienteRepository.findById(id_cliente) == null) {
			throw new APIException("Cliente não Localizado!");
		}
								
		relcompra.setVlTotInvCompras(bitCoinRepository.findTotalInves(id_cliente));
		relcompra.setVlLucro(bitCoinRepository.findTotalLucro(id_cliente, buscaCotacao.getAmount()));
		relcompra.setVlBitCoinDia(buscaCotacao.getAmount());
		relcompra.setSaldoAtualBitCoin(bitCoinRepository.findSaldoAtualBtc(id_cliente, buscaCotacao.getAmount()));
		
		return relcompra;
	}	
	
	@GetMapping("/relatorios/historico/{id_cliente}")
	@ApiOperation(value="Histórico das últimas 5 transações realizadas do Cliente.")
	public List<BitCoins> historicoCliente(@PathVariable(value = "id_cliente") long id_cliente) throws APIException{
		// Busca um Cliente por ID
		if (clienteRepository.findById(id_cliente) == null) {
			throw new APIException("Cliente não Localizado!");
		}	
		
		return bitCoinRepository.findHistoricoCliente(id_cliente);
	}	
	
	
}
