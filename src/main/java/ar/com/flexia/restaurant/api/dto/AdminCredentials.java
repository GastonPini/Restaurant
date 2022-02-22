package ar.com.flexia.restaurant.api.dto;


public class AdminCredentials {

	private String nombre;
	
	private String password;
	
	public AdminCredentials() {
		super();
	}

	public AdminCredentials(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return password;
	}

}