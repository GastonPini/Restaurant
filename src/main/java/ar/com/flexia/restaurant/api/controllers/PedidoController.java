package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.api.dto.Factura;
import ar.com.flexia.restaurant.api.dto.NewPedido;
import ar.com.flexia.restaurant.model.entity.Pedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.core.Authentication;


import java.util.List;


@Tag(name = "Pedido", description = "Controlador de pedidos")
public interface PedidoController {
	
	@Operation(summary = "Crear un pedido para un determinada mesa",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Pedido createPedido(NewPedido request,long mesaId, long restaurantId,
                             List<Integer> productos, Authentication auth);

	@Operation(summary = "Obtener un pedido determinado",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Pedido getPedido(Long pedidoId, long restaurantId, Authentication auth);

	@Operation(summary = "Obtener la lista de pedidos para un determinado restaurant",
            description = "Acción realizada por el operador del respectivo restaurant")
    public List<Pedido> getPedidos(long restaurantId, Authentication auth);

	@Operation(summary = "Agregar un producto para un determinado pedido",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void addProducto(long restaurantId, long pedidoId, long productoId, Authentication auth);
	
	@Operation(summary = "Editar un determinado pedido",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void editarPedido(long restaurantId, long pedidoId, NewPedido pedido, Authentication auth);

	@Operation(summary = "Aplicación de un descuento a un pedido",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void aplicarDescuento (long restaurantId, long mesaId, long pedidoId,
                                  Double porcentajeDescuento, Authentication auth);
	
	@Operation(summary = "Pago de un pedido",
    description = "Acción realizada por el operador del respectivo restaurant")
    public Factura liberarPedido(long restaurantId, long mesaId, long pedidoId, Authentication auth);
    
}