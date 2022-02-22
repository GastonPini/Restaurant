package ar.com.flexia.restaurant.api.controllers.impl;

import java.util.List;

import ar.com.flexia.restaurant.api.controllers.MesaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.services.*;


@RestController
@RequestMapping
public class MesaControllerImpl implements MesaController {

	@Autowired
	private MesaService service;
	
	@Autowired
	private PedidoService pedidoService;
		
	@Secured({ "OPERADOR" })
	@PostMapping(path = "/restaurant/{restaurantId}/mesas/{mesaId}")
	public void createPedido(@RequestBody NewPedido request,
							 @PathVariable("mesaId") long mesaId,
							 @PathVariable("restaurantId") long restaurantId,
							 Authentication auth) {		
		service.createPedido(request, mesaId, restaurantId,
							 Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@GetMapping(path = "/restaurant/{restaurantId}/mesas/{mesaId}/pedidoactivo")
	public Pedido getPedidoActivo(@PathVariable(value = "restaurantId") Long restaurantId,
								  @PathVariable("mesaId") long mesaId,
								  Authentication auth) {
		return service.mostrarPedidoActivo(restaurantId, mesaId,
										   Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@GetMapping(path = "/restaurant/{restaurantId}/mesas/{mesaId}/pedidos/{pedidoId}/productos")
	public List<Producto> getProductos(@PathVariable("pedidoId") long pedidoId,
								       @PathVariable("restaurantId") long restaurantId,
								       Authentication auth) {
		return pedidoService.getProductos(pedidoId, restaurantId,
										  Long.parseLong(auth.getName()));
	}

}