package ar.com.flexia.restaurant.api.dto;

public class NewUser {

	private String nombre;
	
	private String password;
	
	public NewUser() {
		super();
	}

	public NewUser(String nombre, String password) {
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