package ar.com.flexia.restaurant.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_restaurant")
	private long id;
	
	private String nombre;
	
	private String descripcion;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Mesa> mesas;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Delivery> deliveries;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Producto> productos;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;

	public void setId(long id) {
		this.id = id;
	}

	public Restaurant() {}
	
	public Restaurant(String name, String description) {
		super();
		this.nombre = name;
		this.descripcion = description;
		this.mesas = new ArrayList<>();
		this.productos = new ArrayList<>();
	}
	
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public List<Mesa> getMesas() {
		return mesas;
	}
	
	public void addMesa(Mesa mesa) {
		this.mesas.add(mesa);
		mesa.setRestaurant(this);
	}
	
	public List<Delivery> getDeliveries() {
		return deliveries;
	}
	
	public void addDelivery(Delivery delivery) {
		this.deliveries.add(delivery);
		delivery.setRestaurant(this);
	}
	
	public List<Producto> getProductos() {
		return productos;
	}
	
	public void addProducto(Producto producto) {
		this.productos.add(producto);
		producto.setRestaurant(this);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}