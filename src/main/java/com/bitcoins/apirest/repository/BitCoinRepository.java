package com.bitcoins.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bitcoins.apirest.models.BitCoins;

// Responsavel por fazer a persistencia no Banco de Dados
public interface BitCoinRepository extends JpaRepository<BitCoins, Long>{


	@Query(value = "select * from tb_ciet_bitcoins where cliente_id = :id",  nativeQuery = true)
	List<BitCoins> findCompraCliente(@Param("id") Long id);
	
	@Query(value = "select sum(m.vl_Pago) vl_Pago from tb_ciet_bitcoins m where m.cliente_id = :id_cliente",  nativeQuery = true)
	Float findTotalInves(@Param("id_cliente") Long id_cliente);
	
	@Query(value = "select ((sum(m.qtd_bit_coin) * :bitCoins)) - (sum(m.vl_Pago)) lucro from tb_ciet_bitcoins m where m.cliente_id = :id_cliente",  nativeQuery = true)
	Float findTotalLucro(@Param("id_cliente") Long id_cliente, @Param("bitCoins") Float bitCoins);
	
	@Query(value = "select (sum(m.qtd_bit_coin) * :bitCoins) saldo from tb_ciet_bitcoins m where m.cliente_id = :id_cliente",  nativeQuery = true)
	Float findSaldoAtualBtc(@Param("id_cliente") Long id_cliente, @Param("bitCoins") Float bitCoins);
	
	@Query(value = "select * from tb_ciet_bitcoins where cliente_id = :id ORDER BY dh_compra desc LIMIT 5",  nativeQuery = true)
	List<BitCoins> findHistoricoCliente(@Param("id") Long id);
}
