package ar.com.flexia.restaurant.api.controllers.impl;

import java.util.List;

import ar.com.flexia.restaurant.api.controllers.RestaurantController;
import org.springframework.beans.factory.annotation.Autowired;
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

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.services.*;


@RestController
@RequestMapping(path = "/restaurant")
public class RestaurantControllerImpl implements RestaurantController {

	@Autowired
	private RestaurantService service;
	
	@Autowired
	private MesaService mesaService;
	
	@Secured({ "ADMIN" })
	@PostMapping
	public Restaurant createRestaurant(@RequestBody NewRestaurant restaurant) {
		return service.createRestaurant(restaurant);
	}
	
	@Secured({ "ADMIN" })
	@GetMapping
	public List<Restaurant> getRestaurants() {
		return service.findAll();
	}
	
	@Secured({ "ADMIN" })
	@GetMapping(path = "/{restaurantId}")
	public Restaurant getRestaurant(@PathVariable ("restaurantId") Long restaurantId) {
		return service.findById(restaurantId);
	}
	
	@Secured({ "OPERADOR" })
	@PostMapping(path = "/{restaurantId}/mesas")
	public Mesa addMesa(@PathVariable("restaurantId") long restaurantId, @RequestBody NewMesa request, Authentication auth) {
		return service.addMesa(request, restaurantId, Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@GetMapping(path = "/{restaurantId}/mesas")
	public List<Mesa> getMesas(@PathVariable("restaurantId") long restaurantId, Authentication auth) {
		return service.getMesas(restaurantId, Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@PutMapping(path = "/{restaurantId}/mesas/{mesaId}")
	public void updateMesa(@PathVariable("mesaId") long mesaId, @RequestBody NewMesa request, @PathVariable("restaurantId") long restaurantId, Authentication auth) {
		mesaService.updateMesa(mesaId, request, restaurantId, Long.parseLong(auth.getName()));
	}
	
	@Secured({ "OPERADOR" })
	@DeleteMapping(path = "/{restaurantId}/mesas/{mesaId}")
	public void deleteMesa(@PathVariable("mesaId") long mesaId, @PathVariable("restaurantId") long restaurantId, Authentication auth) {
		mesaService.deleteMesa(mesaId, restaurantId, Long.parseLong(auth.getName()));
	}
	
}