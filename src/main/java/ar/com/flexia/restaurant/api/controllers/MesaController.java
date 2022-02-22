package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.api.dto.NewPedido;
import ar.com.flexia.restaurant.model.entity.Pedido;
import ar.com.flexia.restaurant.model.entity.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.core.Authentication;


import java.util.List;


@Tag(name = "Mesa", description = "Controlador de mesas")
public interface MesaController {
	
	@Operation(summary = "Crear un pedido para una determinada mesa",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void createPedido(NewPedido request, long mesaId,
                             long restaurantId, Authentication auth);

	@Operation(summary = "Obtener el pedido activo de una determinada mesa",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Pedido getPedidoActivo(Long restaurantId, long mesaId,
    							  Authentication auth);

	@Operation(summary = "Obtener la lista de productos de un pedido de una mesa",
            description = "Acción realizada por el operador del respectivo restaurant")
    public List<Producto> getProductos(long pedidoId, long restaurantId,
    								   Authentication auth);

}