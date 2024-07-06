package com.royalpaws.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_proveedor")
public class Proveedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prov") private Integer codProv;
	
	@Column(name = "nom_prov") private String nomProv;

	
	// ignora json para buscar por cod(update)
	@JsonIgnore
	@OneToMany(mappedBy = "productoProveedor")
	private List<Producto> listaProductos;

}
