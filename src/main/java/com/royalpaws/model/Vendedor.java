package com.royalpaws.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_vendedor")
public class Vendedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_ven")
	private Integer codVen;
	
	@Column(name = "nom_ven")
	private String nomVen;

	@Column(name = "ape_ven")
	private String apeVen;
	
	@Column(name = "dni_ven")
	private int dniVen;
	
	@Column(name = "ema_ven")
	private String emaVen;
	
	@Column(name = "tel_ven")
	private int telVen;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fec_nac")
	private Date fecNac;
	
	@Column(name = "user_ven")
	private String userVen;
	
	@Column(name = "clave")
	private String clave;
	
	@ManyToOne
	@JoinColumn(name = "cod_rol") // key foranea
	private Rol VendedorRol; // 1.ASOC.

	@JsonIgnore
	@OneToMany(mappedBy = "compraVendedor")
	private List<Compra> listaCompra;
	
	public List<Compra> getListaCompra() {
		return listaCompra;
	}

	public void setgetListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}

	
}
