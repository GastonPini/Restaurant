package ar.com.flexia.restaurant.services;

import ar.com.flexia.restaurant.api.dto.Factura;
import ar.com.flexia.restaurant.api.dto.NewDelivery;
import ar.com.flexia.restaurant.model.entity.Delivery;

import java.util.List;

public interface DeliveryService {
	
    public Delivery crearDelivery(NewDelivery newDelivery, List<Integer> productList, Long restaurantId, Long userId);

    Factura liberarPedido(long restaurantId, long deliveryId, Long parseLong);

    void aplicarDescuento(long restaurantId, long deliveryId, Double porcentajeDescuento, long parseLong);

    void addProducto(long restaurantId, long deliveryId, List<Integer> productList, long parseLong);

	public List<Delivery> findDeliveries(Long restaurantId, Long userId);

}