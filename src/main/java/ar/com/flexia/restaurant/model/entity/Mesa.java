package ar.com.flexia.restaurant.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Mesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mesa")
	private Long id;
	
	private String nombre;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
	private Restaurant restaurant;
	
	@JsonIgnore
	@OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Pedido> pedidos;
	
	private boolean mesaOcupada = false;
	
	public Mesa() {}
	
	public Mesa(String nombre) {
		super();
		this.nombre = nombre;
		this.pedidos = new ArrayList<>();
	}
	
	public Mesa(String nombre, Restaurant restaurant) {
		super();
		this.nombre = nombre;
		this.restaurant = restaurant;
		this.pedidos = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public boolean isMesaOcupada() {
		return mesaOcupada;
	}

	public void setMesaOcupada(boolean mesaOcupada) {
		this.mesaOcupada = mesaOcupada;
	}

	public void addPedido(Pedido pedido) {
		this.pedidos.add(pedido);
		pedido.setMesa(this);
	}

}