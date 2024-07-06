package com.royalpaws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.royalpaws.model.Vendedor;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{
	
		// Para registrar la compra
		@Query("select v from Vendedor v where v.apeVen like ?1")
		public List<Vendedor> listApellidoVendedor(String ape);
	
}
