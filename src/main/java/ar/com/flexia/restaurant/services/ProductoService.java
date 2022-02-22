package ar.com.flexia.restaurant.services;

import java.util.List;

import ar.com.flexia.restaurant.model.entity.*;


public interface ProductoService {

	public Producto createProducto(Producto newProducto, Long restaurantId, Long userId);
	
	public Producto findById(long productoId, Long restauantId, Long userId);
	
	public List<Producto> findAll(Long restauantId, Long userId);

	public Producto updateProducto(long restauantId, long productoId, Producto producto, long userId);
	
	public String cambiarEstadoProducto(long restaurantId, long productoId, Long userId);
	
}