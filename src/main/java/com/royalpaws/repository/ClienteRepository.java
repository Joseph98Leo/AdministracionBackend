package com.royalpaws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.royalpaws.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
    @Query("select c from Cliente c where c.apeCli like ?1")
	public List<Cliente> listApellidoCliente(String ape);
	
	
}
