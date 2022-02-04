package ar.com.flexia.restaurant.api.controllers.impl;

import java.util.List;

import ar.com.flexia.restaurant.api.controllers.ProductoController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.flexia.restaurant.model.entity.Producto;
import ar.com.flexia.restaurant.services.*;


@RestController
@RequestMapping
public class ProductoControllerImpl implements ProductoController {

	private ProductoService service;

	public ProductoControllerImpl(ProductoService service) {
		super();
		this.service = service;
	}

	@Secured({ "OPERADOR" })
	@PostMapping(path = "/restaurant/{restaurantId}/productos")
	public Producto createProducto(@RequestBody Producto request,
							   @PathVariable("restaurantId") long restaurantId,
							   Authentication auth) {
		return service.createProducto(request, restaurantId, Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@GetMapping(path = "restaurant/{restaurantId}/productos/{productoId}")
	public Producto getProducto(@PathVariable("productoId") long productoId,
								@PathVariable("restaurantId") long restaurantId,
								Authentication auth) {
		return service.findById(productoId, restaurantId, Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@GetMapping(path = "/restaurant/{restaurantId}/productos")
	public List<Producto> getProductos(@PathVariable("restaurantId") long restaurantId,
									   Authentication auth) {
		return service.findAll(restaurantId, Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@PutMapping(path = "/restaurant/{restaurantId}/productos/{productoId}")
	public void editProducto(@PathVariable("restaurantId") long restaurantId, @PathVariable ("productoId") long productoId,
							 @RequestBody Producto producto, Authentication auth) {
		service.updateProducto(restaurantId, productoId, producto, Long.parseLong(auth.getName()));
	}

	@Override
	@DeleteMapping(path = "/restaurant/{restaurantId}/productos/{productoId}")
	public String cambiarEstadoProducto(@PathVariable("restaurantId") long restaurantId, @PathVariable ("productoId") long productoId,
								  Authentication auth) {
		return service.cambiarEstadoProducto(restaurantId, productoId, Long.parseLong(auth.getName()));
	}
	
}