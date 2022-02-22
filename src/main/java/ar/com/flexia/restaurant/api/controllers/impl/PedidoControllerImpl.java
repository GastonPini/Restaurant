package ar.com.flexia.restaurant.api.controllers.impl;

import java.util.List;

import ar.com.flexia.restaurant.api.controllers.PedidoController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.services.*;


@RestController
@RequestMapping
public class PedidoControllerImpl implements PedidoController {

	private PedidoService service;

	public PedidoControllerImpl(PedidoService service) {
		super();
		this.service = service;
	}

    @Secured({ "OPERADOR" })
	@PostMapping(path = "/restaurant/{restaurantId}/mesas/{mesaId}/pedidos")
	public Pedido createPedido(@RequestBody NewPedido request,
							   @PathVariable("mesaId") long mesaId,
							   @PathVariable("restaurantId") long restaurantId,
							   @RequestParam(name = "productos", required = false) List<Integer> productos,
							   Authentication auth) {
		return service.createPedido(request, mesaId, restaurantId, productos,
									Long.parseLong(auth.getName()));
	}

    @Secured({ "OPERADOR" })
	@GetMapping(path = "/restaurant/{restaurantId}/pedidos/{pedidoId}")
	public Pedido getPedido(@PathVariable(value = "pedidoId") Long pedidoId,
							@PathVariable("restaurantId") long restaurantId,
							Authentication auth) {
		return service.findById(pedidoId, restaurantId, Long.parseLong(auth.getName()));
	}

    @Secured({ "OPERADOR" })
	@GetMapping(path = "/restaurant/{restaurantId}/pedidos")
	public List<Pedido> getPedidos(@PathVariable("restaurantId") long restaurantId,
								   Authentication auth) {
		return service.findAll(restaurantId, Long.parseLong(auth.getName()));
	}

    @Secured({ "OPERADOR" })
	@PostMapping(path = "restaurant/{restaurantId}/pedidos/{pedidoId}/productos/{productoId}")
	public void addProducto(@PathVariable("restaurantId") long restaurantId,
							@PathVariable("pedidoId") long pedidoId,
							@PathVariable("productoId") long productoId,
							Authentication auth) {
		service.addProducto(restaurantId, pedidoId, productoId,
							Long.parseLong(auth.getName()));
	}
	
    @Secured({ "OPERADOR" })
	@PutMapping(path = "restaurant/{restaurantId}/pedidos/{pedidoId}")
	public void editarPedido(@PathVariable("restaurantId") long restaurantId,
							 @PathVariable("pedidoId") long pedidoId,
							 @RequestBody NewPedido request,
							 Authentication auth) {
		service.editarPedido(restaurantId, pedidoId, request,
							 Long.parseLong(auth.getName()));
	}
	
    @Secured({ "OPERADOR" })
	@PutMapping(path = "restaurant/{restaurantId}/mesas/{mesaId}/pedidos/{pedidoId}/descuento/{porcentajeDescuento}")
	public void aplicarDescuento (@PathVariable("restaurantId") long restaurantId,
								  @PathVariable("mesaId") long mesaId,
								  @PathVariable("pedidoId") long pedidoId,
								  @PathVariable("porcentajeDescuento") Double porcentajeDescuento,
								  Authentication auth){
		service.aplicarDescuento(restaurantId, mesaId, pedidoId, porcentajeDescuento,
								 Long.parseLong(auth.getName()));
	}

    @Secured({ "OPERADOR" })
	@PutMapping(path = "restaurant/{restaurantId}/mesas/{mesaId}/pedidos/{pedidoId}")
	public Factura liberarPedido(@PathVariable("restaurantId") long restaurantId,
								 @PathVariable("mesaId") long mesaId,
								 @PathVariable("pedidoId") long pedidoId,
								 Authentication auth) {
		return service.liberarPedido(restaurantId, mesaId, pedidoId,
									 Long.parseLong(auth.getName()));
	}
	
}