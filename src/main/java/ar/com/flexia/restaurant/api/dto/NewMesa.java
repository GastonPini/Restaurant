package ar.com.flexia.restaurant.api.dto;

import ar.com.flexia.restaurant.model.entity.Mesa;

public class NewMesa {

	private String nombre;
	
	public NewMesa() {
		super();
	}

	public NewMesa(String nombre) {
		this.nombre = nombre;
	}

	public NewMesa(Mesa mesa) {
		this.nombre = mesa.getNombre();
	}

	public String getNombre() {
		return nombre;
	}

}