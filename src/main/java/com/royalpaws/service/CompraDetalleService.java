package com.royalpaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royalpaws.model.CompraDetalle;
import com.royalpaws.repository.CompraDetalleRepository;

@Service
public class CompraDetalleService {
	
	@Autowired
	private CompraDetalleRepository repoCompraDetalle;
	
	public List<CompraDetalle> listaDetalles(){
		return repoCompraDetalle.findAll();
	}
	
	public void eliminar(Integer cod) {
		repoCompraDetalle.deleteById(cod);
	}

}
