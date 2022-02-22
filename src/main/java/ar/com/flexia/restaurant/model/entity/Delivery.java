package ar.com.flexia.restaurant.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String direccion;

    private Long telefono;

    @JsonIgnore
    private Double porcentajeDescuento = 0.0;

    private boolean pagado = false;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Restaurant restaurant;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;

    public Delivery() {}
    
    public Delivery(String direccion, Long telefono) {
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    public Delivery(String direccion, Long telefono, Double porcentajeDescuento) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Delivery(String direccion, Long telefono, Double porcentajeDescuento, Restaurant restaurant) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.porcentajeDescuento = porcentajeDescuento;
        this.restaurant = restaurant;
    }
    
    public Delivery(String direccion, Long telefono, Double porcentajeDescuento, Restaurant restaurant, List<Producto> productos) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.restaurant = restaurant;
        this.porcentajeDescuento = porcentajeDescuento;
        this.productos = productos;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
    
    public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

    public List<Producto> getProductos() {
        return productos;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public void setProductos(List<Producto> productoList) {
        this.productos = productoList;
    }

    public void addProducto (Producto producto){
        this.productos.add(producto);
    }

    @Override
    public String toString() {
        return "PedidoDelivery{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", telefono=" + telefono +
                ", productoList=" + productos +
                '}';
    }

}