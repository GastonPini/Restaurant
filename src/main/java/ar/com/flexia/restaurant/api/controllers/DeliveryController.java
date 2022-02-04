package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.api.dto.Factura;
import ar.com.flexia.restaurant.api.dto.NewDelivery;
import ar.com.flexia.restaurant.model.entity.Delivery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.core.Authentication;


import java.util.List;


@Tag(name = "Pedido delivery", description = "Controlador de pedidos delivery")
public interface DeliveryController {
    
	@Operation(summary = "Cargar un nuevo pedido delivery",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Delivery crearDelivery(NewDelivery newPedidoDelivery, Long restaurantId, List<Integer> productList, Authentication auth);

	@Operation(summary = "Obtener la lista de pedidos delivery",
            description = "Acción realizada por el operador del respectivo restaurant")
    public List<Delivery> getPedidosDelivery(Long restaurantId, Authentication auth);

	@Operation(summary = "Aplicación de un descuento a un pedido delivery",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void aplicarDescuento(long restaurantId, long deliveryId, Double porcentajeDescuento, Authentication auth);

	@Operation(summary = "Pago de un pedido delivery",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Factura liberarPedido(long restaurantId, long deliveryId, Authentication auth);

	@Operation(summary = "Agregar un producto a un nuevo pedido delivery",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void addProducto(long restaurantId, long deliveryId, List<Integer> productList, Authentication auth);

}