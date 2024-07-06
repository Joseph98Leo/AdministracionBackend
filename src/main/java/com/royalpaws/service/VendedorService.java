package com.royalpaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royalpaws.model.Vendedor;
import com.royalpaws.repository.VendedorRepository;

@Service
public class VendedorService {
	@Autowired
	private VendedorRepository repoVendedor;
	
	public void save(Vendedor ven) {
		repoVendedor.save(ven);
	}
	
	public List<Vendedor> listarVendedores(){
		return repoVendedor.findAll();
	}
	
	public void eliminarByID(int id) {
		repoVendedor.deleteById(id);
	}
	
	public Vendedor listById(int id) {
		return repoVendedor.findById(id).get();
	}

	public List<Vendedor> listarVendedorXApellido(String ape_ven){
		return repoVendedor.listApellidoVendedor(ape_ven);
	}

}
