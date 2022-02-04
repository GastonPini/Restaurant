package ar.com.flexia.restaurant.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido")
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
	private String descripcion;

	private Double porcentajeDescuento = 0.0;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY)
	private Mesa mesa;
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private List<Producto> productos;
	
	private boolean pagado = false;
	
	public Pedido() {}
	
	public Pedido(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.productos = new ArrayList<>();
	}
	
	public Pedido(String nombre, String descripcion, Double porcentajeDescuento) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.porcentajeDescuento = porcentajeDescuento;
		this.productos = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Producto> getProductos() {
		return productos;
	}
	
	public void addProducto(Producto producto) {
		this.productos.add(producto);
		producto.addPedido(this);
	}
	
	public void removeProducto(Producto producto) {
		this.productos.remove(producto);
	}
	
	public Mesa getMesa() {
		return mesa;
	}
	
	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(Double descuento) {
		this.porcentajeDescuento = descuento;
	}
	
}