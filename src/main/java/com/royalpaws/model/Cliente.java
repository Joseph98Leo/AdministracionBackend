package com.royalpaws.model;

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
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "tb_cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_cli") private Integer codCli;
	
	@Column(name = "nom_cli") private String nomCli;

	@Column(name = "ape_cli") private String apeCli;
	
	@Column(name = "dni_cli") private int dniCli;
	
	@Column(name = "ema_cli") private String emaCli;
	
	@Column(name = "tel_cli") private int telCli;
	
	@Column(name = "dir_cli") private String dirCli;
	
	@ManyToOne
	@JoinColumn(name = "cod_rol")
	private Rol clienteRol;
	
	@JsonIgnore
	@OneToMany(mappedBy = "compraCliente")
	private List<Compra> listaCompra;

}
