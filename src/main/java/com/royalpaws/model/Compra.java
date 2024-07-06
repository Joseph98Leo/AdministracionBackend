package com.royalpaws.model;

import java.math.BigDecimal;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_compra")
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_compra") private Integer codCom;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fec_compra") private Date fecCom;
	
	@Column(name = "monto_compra") private BigDecimal montoCom;	
	
	@Column(name = "mpago")private String mpago;	
	
	@ManyToOne
	@JoinColumn(name = "cod_cli") private Cliente compraCliente; 
	
	@ManyToOne
	@JoinColumn(name = "cod_ven") private Vendedor compraVendedor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "CDetalleCompra") private List<CompraDetalle> listaCDetalle;
	
	public List<CompraDetalle> getListaCDetalle() {
		return listaCDetalle;
	}

	public void setListaCDetalle(List<CompraDetalle> listaCDetalle) {
		this.listaCDetalle = listaCDetalle;
	}

}
