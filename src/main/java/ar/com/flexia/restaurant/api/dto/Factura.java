package ar.com.flexia.restaurant.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.com.flexia.restaurant.model.entity.*;


public class Factura {

	private Double totalSinDescuento;

	private Double totalConDescuento;
	
	private long fisco;

	private Double descuento = 0.0;

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	private List<Producto> productos;
	
	public Factura() {
		super();
		while(this.fisco <= 0) {
			this.fisco = new Random().nextLong();
		}
	}

	public Factura(Double total) {
		this.totalSinDescuento = total;
		this.fisco = new Random().nextLong();
		this.productos = new ArrayList<>();
	}
	
	public Double getTotal() {
		Double costo = 0.0;
		for(Producto pr : productos)
			costo = costo + pr.getPrecio();

		this.totalSinDescuento = costo;
		this.totalConDescuento = totalSinDescuento - (this.totalSinDescuento / 100) * descuento;
		return totalConDescuento;
	}
	
	public long getFisco() {
		return fisco;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
}