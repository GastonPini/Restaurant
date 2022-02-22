package ar.com.flexia.restaurant.services.impl;

import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.*;
import ar.com.flexia.restaurant.services.*;
import ar.com.flexia.restaurant.api.dto.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;
    
    @MockBean
    RestaurantRepository restaurantRepository;
    
    @MockBean
    PasswordEncoder encoder;
    
    @MockBean
    JWTService jwtService;
    
    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("Creación de un usuario")
    void createUser_Succed() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);
    	
        when(userRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        User userCreado = userService.createUser(user);
        
        assertNotNull(userCreado);
    }

    @Test
    @DisplayName("Creación de un usuario fallido por usuario ya existente")
    void createUser_ShouldFailOnExistingUser() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);
    	
        when(userRepository.findByNombre(anyString())).thenReturn(Optional.of(user));
        
        assertThrows(IllegalArgumentException.class,
        		() -> userService.createUser(user));
    }
    
    @Test
    @DisplayName("Buscar un usuario por su nombre")
    void findUser_Succed() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);
    	
    	when(userRepository.findByNombre(anyString())).thenReturn(Optional.of(user));
    	Optional<User> u = userService.findUser("nombre");
    	
        assertTrue(u.isPresent());
    }

    @Test
    @DisplayName("Buscar un usuario por nombre fallido, usuario inexistente")
    void findUser_ShouldFailOnNoUser() {
    	when(userRepository.findByNombre(anyString())).thenReturn(Optional.empty());
    	Optional<User> user = userService.findUser("nombre");
    	
    	assertFalse(user.isPresent());
    }
    
    @Test
    @DisplayName("Buscar un usuario por su ID")
    void findById_Succed() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);

    	when(userRepository.findById(any())).thenReturn(Optional.of(user));
    	User userEncontrado = userService.findById(1L);

        assertNotNull(userEncontrado);
    }
    
    @Test
    @DisplayName("Buscar un usuario por su ID fallido por usuario inexistente")
    void findById_ShouldFailOnNoUser() {
    	when(userRepository.findById(any())).thenReturn(Optional.empty());

    	assertThrows(RuntimeException.class,
    			() -> userService.findById(1L));
    }
    
    @Test
    @DisplayName("Buscar todos los usuarios")
    void findAll() {
    	assertTrue(userService.findAll().isEmpty());
    	
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);
    	User user2 = new User("Fulano", "lala", UserProfile.OPERADOR, restaurant);

    	when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));
    	List<User> lista = userService.findAll();
    	
    	assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Eliminar un usuario")
    void deleteUser_Succed() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);
    	when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    	
    	userService.deleteUser(1L);
    }
    
    @Test
    @DisplayName("Eliminar un usuario fallido por usuario existente")
    void deleteUser_ShouldFailOnNoUser() {
    	when(userRepository.findById(any())).thenReturn(Optional.empty());
    	doThrow(NoSuchElementException.class).when(userRepository).deleteById(anyLong());
    	
    	assertThrows(RuntimeException.class,
    			() -> userService.deleteUser(1L));
	}
    
    @Test
    @DisplayName("Login exitoso de un usuario")
    void login_Succed() {
		User user = new User("Pepe Test", "test", UserProfile.OPERADOR);
		AdminCredentials creds = new AdminCredentials(user.getNombre(), user.getPassword());
		
		when(userRepository.findByNombre(any())).thenReturn(Optional.of(user));
		when(encoder.matches(any(), any())).thenReturn(true);
		Session sesion = userService.login(creds);
		
		assertNotNull(sesion);
	}
		
	@Test
	@DisplayName("Login de un usuario fallido por contraseña incorrecta")
	void login_ShouldFailOnInvalidPassword() {
		User user = new User("Pepe Test", "test", UserProfile.OPERADOR);
		AdminCredentials creds = new AdminCredentials(user.getNombre(), user.getPassword());
		
		when(userRepository.findByNombre(any())).thenReturn(Optional.of(user));
		when(encoder.matches(any(), any())).thenReturn(false);
		
		assertThrows(RuntimeException.class,
				() -> userService.login(creds));
	}
	
	@Test
	@DisplayName("Login de un usuario fallido usuario inexistente")
	void login_ShouldFailOnInvalidUser() {
		User user = new User("Pepe Test", "test", UserProfile.OPERADOR);
		AdminCredentials creds = new AdminCredentials(user.getNombre(), user.getPassword());
		
		when(userRepository.findByNombre(any())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class,
				() -> userService.login(creds));
	}

	@Test
    @DisplayName("Validación exitosa de un usuario")
	void validarOperador() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);

    	when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    	
    	userService.validarOperador(restaurant.getId(), user.getRestaurant().getId());
	}
	
	@Test
	@DisplayName("Validación de un usuario fallida por usuario inexistente")
	void validarOperador_ShouldFailOnNoUser() {
		when(userRepository.findById(any())).thenReturn(Optional.empty());

    	assertThrows(RuntimeException.class,
    			() -> userService.validarOperador(1L, 1L));
	}
	
	@Test
	@DisplayName("Validación de un usuario fallida por restaurant no correspondiente")
	void validarOperador_ShouldFailOnRestaurant() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	User user = new User("Pepe", "lala", UserProfile.OPERADOR, restaurant);
    	
    	when(userRepository.findById(any())).thenReturn(Optional.of(user));
    	
    	assertThrows(IllegalArgumentException.class,
    			() -> userService.validarOperador(1L, 1L));
	}
    
}