package ar.com.flexia.restaurant.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_configuration")
	private long id;

	private String nombre;

	private float precio;
	
	private boolean activo = true;

	@JsonIgnore
	@ManyToMany(mappedBy = "productos", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	@JsonIgnore
	@ManyToMany(mappedBy = "productos", fetch = FetchType.LAZY)
	private List<Delivery> pedidoDeliveries;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Restaurant restaurant;
	
	public Producto() {}

	public Producto(String nombre, Long precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public Producto(String nombre, Long precio, Restaurant restaurant) {
		this.nombre = nombre;
		this.precio = precio;
		this.pedidos = new ArrayList<>();
		this.setRestaurant(restaurant);
	}

	public List<Delivery> getPedidoDeliveries() {
		return pedidoDeliveries;
	}

	public void setPedidoDeliveries(List<Delivery> pedidoDeliveries) {
		this.pedidoDeliveries = pedidoDeliveries;
	}

	public long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public void addPedido(Pedido pedido) {
		this.pedidos.add(pedido);
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}