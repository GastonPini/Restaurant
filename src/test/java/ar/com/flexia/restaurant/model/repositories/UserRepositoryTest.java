package ar.com.flexia.restaurant.model.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ar.com.flexia.restaurant.model.entity.*;


@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository repo;
	
	@BeforeEach
	void init() {
		if (repo.count() == 0) {
			repo.save(new User("test", "test", UserProfile.ADMIN));
		}
	}
	
	@Test
	@DisplayName("Encuentra un usuario por su nombre")
	void FindByNombre_Succed() {
		Optional<User> user = repo.findByNombre("test");
		assertTrue(user.isPresent());
	}
}