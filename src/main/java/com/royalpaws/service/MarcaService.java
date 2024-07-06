package com.royalpaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royalpaws.model.Marca;
import com.royalpaws.repository.MarcaRepository;


@Service
public class MarcaService {
	
		@Autowired
		private MarcaRepository marcaRepo;
		
		public List<Marca> listarMarcas(){
			return marcaRepo.findAll();
		}
		
		public void grabar(Marca bean) {
			marcaRepo.save(bean);
		}
		
		public Marca buscar(Integer cod) {
			return marcaRepo.findById(cod).orElse(null);
		}
		
		public void eliminar(Integer cod) {
			marcaRepo.deleteById(cod);
		}
		
}
