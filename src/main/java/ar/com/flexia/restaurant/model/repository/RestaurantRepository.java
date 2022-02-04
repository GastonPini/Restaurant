package ar.com.flexia.restaurant.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.flexia.restaurant.model.entity.*;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	public Optional<Restaurant> findByNombre(String nombre);

	public Optional<Restaurant> findById(long restaurantId);
	
	Optional<Restaurant> findByIdAndProductos_Nombre(long restaurantId, String nombre);
	
}