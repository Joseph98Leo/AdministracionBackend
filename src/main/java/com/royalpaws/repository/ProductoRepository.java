package com.royalpaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.royalpaws.model.Producto;

import jakarta.transaction.Transactional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	
		@Transactional
		@Modifying
		@Query("update Producto p set p.img_prod=?1, p.nomArchivo=?2 where p.codProd=?3")
		public void actualizarImagen(byte[] img, String nomAr, Integer cod);
	
}
