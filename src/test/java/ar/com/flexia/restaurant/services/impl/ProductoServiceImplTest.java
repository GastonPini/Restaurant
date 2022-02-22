package ar.com.flexia.restaurant.services.impl;

import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.ProductoRepository;
import ar.com.flexia.restaurant.model.repositories.RestaurantRepository;
import ar.com.flexia.restaurant.model.repositories.UserRepository;
import ar.com.flexia.restaurant.services.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ProductoServiceImplTest {

    @Autowired
    ProductoService productoService;

    @MockBean
    ProductoRepository productoRepository;
    
    @MockBean
    RestaurantRepository restaurantRepository;
    
    @MockBean
    UserRepository userRepository;
    
    @MockBean
    UserService userService;

    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("Creación de un producto")
    void createProducto_Succed() {
        Producto producto = new Producto("papas", 1000L);
        Restaurant restaurant = new Restaurant("rest1", "rest1");

        when(restaurantRepository.findByIdAndProductos_Nombre(anyLong(), anyString()))
                .thenReturn(Optional.empty());
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));
        when(productoRepository.save(any())).thenReturn(producto);

        Producto productoCreado = productoService.createProducto(producto, 1L, 1L);
        
        assertNotNull(productoCreado);
    }

    @Test
    @DisplayName("Creación de un producto fallido por ya existir el producto")
    void createProducto_ShouldFailOnExistingProduct() {
        Producto producto = new Producto("papas", 1000L);
        Restaurant restaurant = new Restaurant("rest1", "rest1");

        when(restaurantRepository.findByIdAndProductos_Nombre(anyLong(), anyString()))
                .thenReturn(Optional.of(restaurant));

        assertThrows(IllegalArgumentException.class,
        		() -> productoService.createProducto(producto, 1L, 1L));
    }
    
    @Test
    @DisplayName("Creación de un producto fallido por no existir el restaurant")
    void createProducto_ShouldFailOnNoRestaurant() {
    	Producto producto = new Producto("papas", 1000L);

        when(restaurantRepository.findByIdAndProductos_Nombre(anyLong(), anyString()))
        		.thenReturn(Optional.empty());
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
        		() -> productoService.createProducto(producto, 1L, 1L));
    }

    @Test
    @DisplayName("Buscar un producto por su ID")
    void findById_Succed() {
    	Producto producto = new Producto("papas", 1000L);
    	when(productoRepository.findById(any())).thenReturn(Optional.of(producto));

        Producto productoEncontrado = productoService.findById(1L, 1L, 1L);

        assertNotNull(productoEncontrado);
        System.out.println(productoEncontrado);
    }
    
    @Test
    @DisplayName("Buscar un producto por su ID fallido por no existir el producto")
    void findById_ShouldFailOnNoProduct() {
    	when(productoRepository.findById(any())).thenReturn(Optional.empty());

    	assertThrows(RuntimeException.class,
    			() -> productoService.findById(1L, 1L, 1L));
    }

    @Test
    @DisplayName("Buscar lista de productos")
    void findAll_Succed() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));
    	
    	assertTrue(productoService.findAll(1L, 1L).isEmpty());
    }
    
    @Test
    @DisplayName("Buscar lista de productos fallida por no existir el restaurant")
    void findAll_ShouldFailOnNoRestaurant() {
    	when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(NoSuchElementException.class,
    			() -> productoService.findAll(1L, 1L).isEmpty());
    }

    @Test
    @DisplayName("Actualizar un producto")
    void updateProducto_Succed() {
    	Producto producto = new Producto("pinta", 1000L);
    	Producto datosNuevos = new Producto("papas", 700L);
    	when(productoRepository.findById(any())).thenReturn(Optional.of(producto));
    	productoService.updateProducto(1L, 1L, datosNuevos, 1L);
    	assertEquals(datosNuevos.getNombre(), producto.getNombre());
    	
    	Producto producto2 = new Producto("coca-cola", 1000L);
    	datosNuevos = new Producto(null, 0L);
    	when(productoRepository.findById(any())).thenReturn(Optional.of(producto2));
    	productoService.updateProducto(1L, 1L, datosNuevos, 1L);
    	assertNotEquals(datosNuevos, producto2);
    }
    
    @Test
    @DisplayName("Actualizar un producto fallido por no existir el producto")
    void updateProducto_ShouldFailOnNoProduct() {
    	when(productoRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(RuntimeException.class,
    			() -> productoService.findById(1L, 1L, 1L));
    }
    
    @Test
    @DisplayName("Cambiar el estado de un producto")
    void cambiarEstadoProducto() {
    	Producto producto1 = new Producto("papas", 700L);
    	when(productoRepository.findById(any())).thenReturn(Optional.of(producto1));
    	String mensaje = productoService.cambiarEstadoProducto(1L, 1L, 1L);
    	assertNotNull(mensaje);
    	
    	Producto producto2 = new Producto("pizza", 1000L);
    	producto2.setActivo(false);
    	when(productoRepository.findById(any())).thenReturn(Optional.of(producto2));
    	mensaje = productoService.cambiarEstadoProducto(1L, 1L, 1L);
    	assertNotNull(mensaje);
    }

    @Test
    @DisplayName("Cambiar el estado de un producto fallido por no existir el producto")
    void cambiarEstadoProducto_ShouldFailOnNoProduct() {
    	when(productoRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(RuntimeException.class,
    			() -> productoService.findById(1L, 1L, 1L));
    }
    
}