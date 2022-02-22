package ar.com.flexia.restaurant.model.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.flexia.restaurant.model.entity.*;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	public Optional<Producto> findById(Long productoId);

	public Optional<Producto> findByNombre(String nombre);
	
	public Optional<Producto> findProductoByIdAndRestaurant_Id(long productoId, long restaurantId);

}