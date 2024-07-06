package com.royalpaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royalpaws.model.Producto;
import com.royalpaws.repository.ProductoRepository;


@Service
public class ProductoService {
	
		@Autowired
		private ProductoRepository productoRepo;
		
		public List<Producto> listarProductos() {
			return productoRepo.findAll();
		}

		public void grabar(Producto bean) {
			productoRepo.save(bean);
		}

		public Producto buscar(Integer cod) {
			return productoRepo.findById(cod).orElse(null);
		}

		public void eliminar(Integer cod) {
			productoRepo.deleteById(cod);
		}
		
		public Producto listById(int id) {
			return productoRepo.findById(id).get();
		}
		
		public void actualizarImg(byte[] img, String nomAr, Integer cod) {
			productoRepo.actualizarImagen(img, nomAr, cod);
		}
}
