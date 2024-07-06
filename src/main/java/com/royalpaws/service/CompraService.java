package com.royalpaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royalpaws.model.Compra;
import com.royalpaws.model.CompraDetalle;
import com.royalpaws.repository.CompraDetalleRepository;
import com.royalpaws.repository.CompraRepository;

@Service
public class CompraService {
	
	@Autowired
	private CompraRepository repoCompra;
	@Autowired 
	private CompraDetalleRepository repoCDetalle;
	
	public List<Compra> listarCompras() {
		return repoCompra.findAll();
	}

	public Compra buscar(Integer cod) {
		return repoCompra.findById(cod).get();
	}

	public void eliminar(Integer cod) {
		repoCompra.deleteById(cod);
	}
	
	@Transactional
	public void registrarCompra(Compra bean) throws Exception {
	    try {
	        // GrabarCompra
	        repoCompra.save(bean);
	        
	        // GrabarCompraDetalle
	        if (bean.getListaCDetalle() != null) {
	            for (CompraDetalle cd : bean.getListaCDetalle()) {
	                cd.setCodDetCom(bean.getCodCom());
	                repoCDetalle.save(cd);
	            }
	        }
	    } catch (DataAccessException ex) {
	        throw new Exception("Error al guardar la información de la compra o sus detalles: " + ex.getMessage(), ex);
	    }
	}

	public void actualizar(Compra bean) {
		repoCompra.save(bean);
	}
}
