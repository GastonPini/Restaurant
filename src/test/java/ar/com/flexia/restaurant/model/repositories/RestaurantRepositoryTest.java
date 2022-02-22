package ar.com.flexia.restaurant.model.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.services.RestaurantService;


@DataJpaTest
class RestaurantRepositoryTest {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	RestaurantService restaurantService;
	
	Long restaurantId;
	
	@BeforeEach
	void init() {
		if (restaurantRepository.count() == 0) {
			Restaurant r = restaurantRepository.save(new Restaurant("rest1", "rest1"));
			this.restaurantId = r.getId();
		}
	}
	
	// Los métodos que utilizan un solo atributo o una sola clase
	// (ej: FindById, FindByNombre), no valen la pena como para ser testeados.
	// Mejor invertir tiempo en testear métodos más complejos.
	@Test
	@DisplayName("Encuentra un restaurant por su nombre")
	void FindByNombre_Succed() {
		Optional<Restaurant> restaurant = restaurantRepository.findByNombre("rest1");
		assertTrue(restaurant.isPresent());
	}
	
	@Test
	@DisplayName("Encuentra un restaurant por su ID")
	void FindById_Succed() {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		assertTrue(restaurant.isPresent());
	}
	
	// En los repositorios, los testeos se realizan solo para los "caminos felices",
	// porque los "caminos tristes" están fuera del alcance de dichas clases.
	@Test
	@DisplayName("Encuentra un restaurant por su ID y nombre de un producto")
	void findByIdAndProductos_Nombre() {
		Producto producto = new Producto("papas", 1000L);
		Restaurant restaurant = new Restaurant("rest1", "rest1");
		
		restaurant.addProducto(producto);
		restaurantRepository.save(restaurant);
		
		Optional<Restaurant> resultado1 = restaurantRepository
				.findByIdAndProductos_Nombre(restaurant.getId(), "papas");
		assertTrue(resultado1.isPresent());
		
		Optional<Restaurant> resultado2 = restaurantRepository
				.findByIdAndProductos_Nombre(restaurant.getId(), "pizza");
		assertFalse(resultado2.isPresent());
	}
}