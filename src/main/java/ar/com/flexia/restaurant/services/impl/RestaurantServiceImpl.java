package ar.com.flexia.restaurant.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repository.*;
import ar.com.flexia.restaurant.services.*;


@Service
public class RestaurantServiceImpl implements RestaurantService {

	private final RestaurantRepository repo;

	private final MesaRepository mesaRepo;

	private final UserService userService;

	public RestaurantServiceImpl(RestaurantRepository repo, MesaRepository mesaRepo, UserService userService) {
		super();
		this.repo = repo;
		this.mesaRepo = mesaRepo;
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public Restaurant createRestaurant(NewRestaurant newRestaurant) {
		
		Optional<Restaurant> restaurant = repo.findByNombre(newRestaurant.getNombre());
		
		if(restaurant.isPresent())
			throw new IllegalArgumentException("El restaurant " + newRestaurant.getNombre() + " ya existe, ingrese otro nombre.");
		
		Restaurant rest = new Restaurant(newRestaurant.getNombre(), newRestaurant.getDescripcion());

		userService.createUser(new User(newRestaurant.getUsername(), newRestaurant.getPassword(), UserProfile.OPERADOR,rest ));
		
		return rest;

	}
	
	@Override
	@Transactional
	public Optional<Restaurant> findRestaurant(String nombre, Long restaurantId, Long userId) {
		return repo.findByNombre(nombre);
	}
	
	@Override
	@Transactional
	public Restaurant findById(long restaurantId) {
		return repo.findById(restaurantId)
				.orElseThrow(() -> new APIException("Restaurant no encontrado"));
	}
	
	@Override
	@Transactional
	public List<Restaurant> findAll() {
		return repo.findAll();
	}
	
	@Override
	@Transactional
	public List<Mesa> getMesas(Long restaurantId, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		Restaurant r = repo.findById(restaurantId).orElseThrow(NoSuchElementException::new);
	
		return r.getMesas();
	
	}
	
	@Override
	@Transactional
	public Mesa addMesa(NewMesa mesa, Long restaurantId, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
			
		Optional<Mesa> m = mesaRepo.findByNombreAndRestaurant_Id(mesa.getNombre(), restaurantId);
		
		if(m.isPresent())
			throw new IllegalArgumentException("La mesa " + mesa.getNombre() + " ya existe para el restaurant.");
		
		else {
			Restaurant restaurant = repo.findById(restaurantId).orElseThrow(()
					-> new NoSuchElementException("Restaurant inexistente."));
			Mesa me = new Mesa(mesa.getNombre());
			restaurant.addMesa(me);
			repo.save(restaurant);
			return me;
		}
		
	}
	
}