package ar.com.flexia.restaurant.services;

import java.util.List;
import java.util.Optional;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;


public interface RestaurantService {

	public Restaurant createRestaurant(NewRestaurant newRestaurant);
	
	public Optional<Restaurant> findRestaurant(String name, Long restaurantId, Long userId);

	public Restaurant findById(long restaurantId);
	
	public List<Restaurant> findAll();	
	
	public List<Mesa> getMesas(Long restaurantId, Long userId);
	
	public Mesa addMesa(NewMesa mesa, Long restaurantId, Long userId);

}