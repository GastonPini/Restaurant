package ar.com.flexia.restaurant.api.dto;

import ar.com.flexia.restaurant.model.entity.Restaurant;

public class NewRestaurant {

	private String nombre;
	
	private String descripcion;
	
	private String username;
	
	private String password;
	
	public NewRestaurant() {
		super();
	}
	
	public NewRestaurant(Restaurant restaurant, String username, String password) {
		this.nombre = restaurant.getNombre();
		this.descripcion = restaurant.getDescripcion();
		this.username = username;
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}