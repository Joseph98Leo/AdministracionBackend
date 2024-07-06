package com.royalpaws.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royalpaws.model.Proveedor;
import com.royalpaws.repository.ProveedorRepository;
@Service
public class ProveedorService {
	
		@Autowired
		private ProveedorRepository ProveedorRepo;

		public List<Proveedor> listarProveedores() {
			return ProveedorRepo.findAll();
		}

		public void grabar(Proveedor bean) {
			ProveedorRepo.save(bean);
		}

		public Proveedor buscar(Integer cod) {
			return ProveedorRepo.findById(cod).orElse(null);
		}

		public void eliminar(Integer cod) {
			ProveedorRepo.deleteById(cod);
		}
}
