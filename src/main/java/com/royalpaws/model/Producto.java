package com.royalpaws.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_producto")
public class Producto {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prod") private Integer codProd;
	
	@Column(name = "nom_prod") private String nomProd;
	
	@Column(name = "des_prod") private String desProd;
	
	@Column(name = "cat_prod") private String catProd;
	
	@Column(name = "stock_prod") private int stockProd;
	
	@Column(name = "precio_compra") private BigDecimal precioCompra;
	
	// Sin nombre de columna
	private byte[] img_prod;
	
	@Column(name = "nom_archivo") private String nomArchivo;

	@ManyToOne
	@JoinColumn(name = "cod_marca")
	private Marca productoMarca;

	@ManyToOne
	@JoinColumn(name = "cod_prov")
	private Proveedor productoProveedor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "CDetalleProducto")
	private List<CompraDetalle> listaCDetalle;

	public List<CompraDetalle> getListaCDetalle() {
		return listaCDetalle;
	}

	public void setListaCDetalle(List<CompraDetalle> listaCDetalle) {
		this.listaCDetalle = listaCDetalle;
	}
	
	public String getNomArchivo() {
		return nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}
}
