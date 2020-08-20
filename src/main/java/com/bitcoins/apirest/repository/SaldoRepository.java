package com.bitcoins.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.bitcoins.apirest.models.Saldos;
// Responsavel por fazer a persistencia no Banco de Dados
public interface SaldoRepository extends JpaRepository<Saldos, Long>{

	//Busca por ID Cliente.
	Saldos findById(long id);
	
	@Query(value = "select * from tb_ciet_saldos where cliente_id = :id",  nativeQuery = true)
	List<Saldos> findSaldoCliente(@Param("id") Long id);
	
}
