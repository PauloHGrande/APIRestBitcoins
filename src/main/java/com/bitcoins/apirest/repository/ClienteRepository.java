package com.bitcoins.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitcoins.apirest.models.Clientes;
// Responsavel por fazer a persistencia no Banco de Dados
public interface ClienteRepository extends JpaRepository<Clientes, Long>{

	
	//Busca por ID Cliente.
	Clientes findById(long id);
}
