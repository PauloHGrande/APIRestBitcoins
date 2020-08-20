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
}
