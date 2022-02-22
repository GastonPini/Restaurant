package ar.com.flexia.restaurant.services;

import java.util.List;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;


public interface PedidoService {

	public Pedido createPedido(NewPedido newPedido, Long mesaId, Long restaurantId, List<Integer> productos, Long userId);
	
	public Pedido findById(long pedidoId, Long restaurantId, Long userId);
	
	public List<Pedido> findAll(Long restaurantId, Long userId);

	public void addProducto(long restaurantId, long pedidoId, long productoId, Long userId);

	public List<Producto> getProductos(long pedidoId, Long restaurantId, Long userId);

	public void editarPedido(long restaurantId, long pedidoId, NewPedido pedido, long userId);
	
	public Factura liberarPedido(long restaurantId, long mesaId, long pedidoId, Long userId);

	public void aplicarDescuento(long restaurantId, long mesaId, long pedidoId, Double porcentajeDescuento, long userId);

}