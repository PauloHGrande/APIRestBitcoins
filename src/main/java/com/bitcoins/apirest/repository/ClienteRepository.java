package com.bitcoins.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bitcoins.apirest.models.Clientes;

// Responsavel por fazer a persistencia no Banco de Dados
public interface ClienteRepository extends JpaRepository<Clientes, Long>{

	
	//Busca por ID Cliente.
	Clientes findById(long id);
	
	@Query(value = "select * from tb_ciet_cliente where id = :id",  nativeQuery = true)
	List<Clientes> findSaldoCliente(@Param("id") Long id);
}
