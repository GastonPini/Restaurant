package ar.com.flexia.restaurant.model.repositories;

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
class DeliveryRepositoryTest {

	// Los testeos de repositorios no utilizan @MockBean,
	// directamente importan las clases.
	
	@Autowired
	DeliveryRepository deliveryRepository;
	
	RestaurantService restaurantService;
	
	RestaurantRepository restaurantRepository;
	
	Long deliveryId;
	
	@BeforeEach
	void init() {
		if (deliveryRepository.count() == 0) {
			Restaurant restaurant = new Restaurant("rest1", "rest1");
			Delivery d = deliveryRepository.save(new Delivery("test", 123345L, 0.0, restaurant));
			this.deliveryId = d.getId();
		}
	}

	// Algunos métodos que vienen incluidos dentro de JPA como findById
	// son demasiado triviales como para ser testeados.
	// (Este findById queda por ya haberlo hecho antes). 
	@Test
	@DisplayName("Encuentra un delivery por su ID")
	void findById_Succed() {
		Optional<Delivery> delivery = deliveryRepository.findById(deliveryId);
		assertTrue(delivery.isPresent());
	}
}