package com.royalpaws.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data

public class Detalle implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private int codigo;
	private int cantidad;
	private String nombre;
	private BigDecimal precio;

}
