package ar.com.flexia.restaurant.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	private Long id;

	@Column(unique = true)
	private String nombre;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserProfile profile;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Restaurant restaurant;

	public User() {}
	
	public User(String nombre, String password, UserProfile profile) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.profile = profile;
	}
	
	public User(String nombre, String password, UserProfile profile, Restaurant restaurant) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.profile = profile;
		this.restaurant = restaurant;
		restaurant.setUser(this);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	public UserProfile getProfile() {
		return profile;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

}