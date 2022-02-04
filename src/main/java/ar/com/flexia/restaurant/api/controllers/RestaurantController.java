package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.api.dto.NewMesa;
import ar.com.flexia.restaurant.api.dto.NewRestaurant;
import ar.com.flexia.restaurant.model.entity.Mesa;
import ar.com.flexia.restaurant.model.entity.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Tag(name = "Restarurant", description = "Controlador de restaurants")
public interface RestaurantController {
	
	@Operation(summary = "Cargar un nuevo restaurant",
            description = "Acción realizada por el admin")
    public Restaurant createRestaurant(@RequestBody NewRestaurant restaurant);

	@Operation(summary = "Obtener la lista de restaurants",
            description = "Acción realizada por el admin")
    public List<Restaurant> getRestaurants();

	@Operation(summary = "Obtener un determinado restaurant",
            description = "Acción realizada por el admin")
    public Restaurant getRestaurant(Long restaurantId);

	@Operation(summary = "Agregar una mesa a un restaurant",
            description = "Acción realizada por el operador del respectivo restaurant")
    public Mesa addMesa(long restaurantId, NewMesa request, Authentication auth);

	@Operation(summary = "Obtener la lista de mesas de un restaurant",
            description = "Acción realizada por el operador del respectivo restaurant")
    public List<Mesa> getMesas(long restaurantId, Authentication auth);

	@Operation(summary = "Actualización de los datos de una determinada mesa",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void updateMesa(long mesaId, NewMesa request, long restaurantId, Authentication auth);

	@Operation(summary = "Eliminación de una determinada mesa",
            description = "Acción realizada por el operador del respectivo restaurant")
    public void deleteMesa(long mesaId, long restaurantId, Authentication auth);

}