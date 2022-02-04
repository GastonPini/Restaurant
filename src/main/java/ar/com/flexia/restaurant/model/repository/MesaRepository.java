package ar.com.flexia.restaurant.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.flexia.restaurant.model.entity.*;


@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

	public Optional<Mesa> findById(Long mesaId);
	
	public Optional<Mesa> findByNombreAndRestaurant(String nombre, Optional<Restaurant> restaurant);
	
	public Optional<Mesa> findByNombreAndRestaurant(String nombre, Restaurant restaurant);
	
	Optional<Mesa> findByIdAndRestaurant_Nombre(long mesaId, String nombre);
	
	Optional<Mesa> findByNombreAndRestaurant_Id(String nombre, long restaurantId);

}