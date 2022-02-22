package ar.com.flexia.restaurant.services.impl;

import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.MesaRepository;
import ar.com.flexia.restaurant.model.repositories.RestaurantRepository;
import ar.com.flexia.restaurant.services.*;
import ar.com.flexia.restaurant.api.dto.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class RestaurantServiceImplTest {

    @Autowired
    RestaurantService restaurantService;

    @MockBean
    RestaurantRepository restaurantRepository;

    @MockBean
    MesaRepository mesaRepository;

    @MockBean
    UserService userService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("Creacion exitosa de restaurant")
    void createRestaurantOK() {
        when(restaurantRepository.findByNombre(any())).thenReturn(Optional.empty());
        User user = new User("pepe", "pepe", UserProfile.OPERADOR);
        Restaurant restaurant = new Restaurant("rest1", "rest1");
        user.setRestaurant(restaurant);
        restaurant.setUser(user);
        NewRestaurant newRestaurant = new NewRestaurant(restaurant, user.getNombre(), user.getPassword());

        Restaurant r = restaurantService.createRestaurant(newRestaurant);
        assertAll(() -> assertNotNull(r),
                  () -> assertEquals(user.getNombre(), r.getUser().getNombre()),
                  () -> assertEquals(user.getPassword(), r.getUser().getPassword()),
                  () -> assertEquals(restaurant.getDescripcion(), r.getDescripcion()),
                  () -> assertEquals(restaurant.getProductos(),r.getProductos()),
                  () -> assertEquals(restaurant.getNombre(), r.getNombre()));
        System.out.print(r);
    }

    @Test
    @DisplayName("Creacion fallida de restaurant debido a nombre existente")
    void createRestaurantFAIL_RestaurantExisteConEseNombre() {
        Restaurant restaurant = new Restaurant("rest1", "rest1");
        NewRestaurant newRestaurant = new NewRestaurant(restaurant, "pepe", "pepe");
        when(restaurantRepository.findByNombre(any())).thenReturn(Optional.of(restaurant));
        assertThrows(RuntimeException.class, () -> restaurantService.createRestaurant(newRestaurant));
    }

    @Test
    @DisplayName("Encuentra al restaurant por nombre de manera exitosa")
    void findRestaurant() {
        Restaurant restaurant = new Restaurant("rest1" , "rest1");
        restaurant.setUser(new User("pepe", "pepe", UserProfile.OPERADOR));
        when(restaurantRepository.findByNombre(any())).thenReturn(Optional.of(restaurant));
        Optional <Restaurant> restaurantEncontrado = restaurantService.findRestaurant("rest1", 1L, 1L);
        assertNotNull(restaurantEncontrado);
        restaurantEncontrado.ifPresent(value -> assertAll(
                () -> assertEquals(restaurant.getNombre(), value.getNombre()),
                () -> assertEquals(restaurant.getDescripcion(), value.getDescripcion()),
                () -> assertEquals(restaurant.getUser(), value.getUser())
        ));
    }

    @Test
    @DisplayName("Encuentra al restaurant por id de manera exitosa")
    void findById() {
        Restaurant restaurant = new Restaurant("rest1" , "rest1");
        restaurant.setUser(new User("pepe", "pepe", UserProfile.OPERADOR));
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        Restaurant restaurantEncontrado = restaurantService.findById(1L);
        assertNotNull(restaurantEncontrado);
        assertAll(
                () -> assertEquals(restaurant.getNombre(), restaurantEncontrado.getNombre()),
                () -> assertEquals(restaurant.getDescripcion(), restaurantEncontrado.getDescripcion()),
                () -> assertEquals(restaurant.getUser(), restaurantEncontrado.getUser())
        );
    }

    @Test
    @DisplayName("No encuentra al restaurant con ese id")
    void findByIdFAIL_NoEncontrado() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(APIException.class,
        		() -> restaurantService.findById(1L));
    }

    @Test
    @DisplayName("Encuentra todos los restaurant existentes de manera exitosa")
    void findAll() {
        Restaurant rest1 = new Restaurant("rest1", "rest1");
        Restaurant rest2 = new Restaurant("rest2", "rest2");

        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(rest1, rest2));

        assertEquals(Arrays.asList(rest1, rest2) ,restaurantService.findAll());
    }

    // Esto quizás conviene hacerlo en dos métodos separados
    @Test
    @DisplayName("Encuentra las mesas de un determinado restaurant de manera exitosa")
    void getMesas() {
        Restaurant restaurant = new Restaurant("rest1", "rest1");
        Mesa mesa = new Mesa("mesa1");
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));
        assertTrue(restaurantService.getMesas(1L, 1L).isEmpty());

        restaurant.addMesa(mesa);

        assertTrue(restaurantService.getMesas(1L, 1L).contains(mesa));
    }

    @Test
    @DisplayName("Restaurant inexistente, no puede devolver las mesas")
//  Esto quizás conviene hacerlo en dos métodos separados
    void getMesasFAIL_RestaurantNoEncontrado() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
        		() -> restaurantService.getMesas(1L, 1L));
    }

    @Test
    @DisplayName("Agrega una mesa a un restaurant de manera exitosa")
    void addMesaOK() {
        Restaurant restaurant = new Restaurant("rest1", "rest1");
        when(mesaRepository.findByNombreAndRestaurant_Id(anyString(), anyLong())).thenReturn(Optional.empty());
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));

        NewMesa newMesa = new NewMesa("mesa1");

        assertNotNull(restaurantService.addMesa(newMesa, 1L, 1L));
    }

    @Test
    @DisplayName("No se puede agregar la mesa, existe una con el mismo nombre en este restaurant")
    void addMesaFAIL_MesaExistenteParaEseRestaurant() {
        when(mesaRepository.findByNombreAndRestaurant_Id(anyString(),anyLong()))
                .thenReturn(Optional.of(new Mesa("mesa1")));
        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.addMesa(new NewMesa("mesa1"), 1L, 1L));
    }

    @Test
    @DisplayName("No se puede agregar la mesa, busca el restaurant por id pero no lo encuentra")
    void addMesaFAIL_RestaurantInexistente() {
        when(mesaRepository.findByNombreAndRestaurant_Id(anyString(),anyLong()))
                .thenReturn(Optional.empty());
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> restaurantService.addMesa(new NewMesa("mesa1"), 1L, 1L));
    }
    
}