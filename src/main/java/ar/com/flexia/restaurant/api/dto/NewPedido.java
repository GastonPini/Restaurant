package ar.com.flexia.restaurant.api.dto;

import ar.com.flexia.restaurant.model.entity.*;

public class NewPedido {

	private String nombre;
	
	private String descripcion;
	
	private Double porcentajeDescuento = 0.0;
	
	public NewPedido() {
		super();
	}

	public NewPedido(Pedido pedido) {
		this.nombre = pedido.getNombre();
		this.descripcion = pedido.getDescripcion();
		this.porcentajeDescuento = pedido.getPorcentajeDescuento();
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	
}