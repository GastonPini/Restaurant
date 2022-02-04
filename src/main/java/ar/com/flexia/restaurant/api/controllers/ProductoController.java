package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.model.entity.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.core.Authentication;


import java.util.List;


@Tag(name = "Producto", description = "Controlador de productos")
public interface ProductoController {
 
	@Operation(summary = "Crear un producto para un determinado restaurant",
            description = "Acción realizada por el operador del respectivo restaurant")
	public Producto createProducto(Producto request, long restaurantId, Authentication auth);

	@Operation(summary = "Obtener un determinado producto",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Producto getProducto(long productoId, long restaurantId, Authentication auth);

	@Operation(summary = "Obtener la lista de productos de un determinado restaurant",
            description = "Acción realizada por el operador del respectivo restaurant")
    public List<Producto> getProductos(long restaurantId, Authentication auth);

	@Operation(summary = "Editar los datos de un producto determinado",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void editProducto(long restaurantId,long productoId, 
                             Producto producto, Authentication auth);
	
	@Operation(summary = "Inactivar un producto determinado para la venta",
            description = "Acción realizada por el operador del respectivo restaurant")
	public String cambiarEstadoProducto(long restaurantId, long productoId, Authentication auth);

}