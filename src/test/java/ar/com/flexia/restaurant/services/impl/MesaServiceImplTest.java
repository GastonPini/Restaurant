package ar.com.flexia.restaurant.services.impl;

import ar.com.flexia.restaurant.api.dto.NewMesa;
import ar.com.flexia.restaurant.api.dto.NewPedido;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.MesaRepository;
import ar.com.flexia.restaurant.model.repositories.PedidoRepository;
import ar.com.flexia.restaurant.model.repositories.RestaurantRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
class MesaServiceImplTest {

    @Autowired
    MesaService mesaService;

    @MockBean
    MesaRepository mesaRepository;
    
    @MockBean
    RestaurantRepository restaurantRepository;
    
    @MockBean
    PedidoRepository pedidoRepository;
    
    @MockBean
    UserService userService;

    @AfterEach
    void tearDown() {}

    // Los matchers (any(), anyLong(), anyString()) solo se utilizan
    // dentro de los mock (when)
    @Test
    @DisplayName("Buscar lista de mesas")
    void findAll_Succed() {
    	Restaurant restaurant = new Restaurant("rest1", "rest1");
    	when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));
    	
    	assertTrue(mesaService.findAll(1L, 1L).isEmpty());
    }
    
    @Test
    @DisplayName("Buscar lista de mesas fallida por no existir el restaurant")
    void findAll_ShouldFailOnNoRestaurant() {
    	when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertTrue(mesaService.findAll(1L, 1L).isEmpty());
    }

    @Test
    @DisplayName("Mostrar el pedido activo de una mesa")
    void mostrarPedidoActivo_Succed() {
    	Pedido pedido = new Pedido("pedido full", "picada completa", 0.0);
    	
    	when(pedidoRepository.findPedidoByPagadoIsFalseAndMesa_Id(anyLong())).thenReturn(Optional.of(pedido));
    	mesaService.mostrarPedidoActivo(1L, 1L, 1L);
    	
    	assertFalse(pedido.isPagado());	
    }
    
    @Test
    @DisplayName("Mostrar el pedido activo fallido, por mesa inexistente")
    void mostrarPedidoActivo_ShouldFailOnNoMesa() {
    	when(pedidoRepository.findPedidoByPagadoIsFalseAndMesa_Id(anyLong())).thenReturn(Optional.empty());
    	
    	assertThrows(RuntimeException.class,
    			() -> mesaService.mostrarPedidoActivo(1L, 1L, 1L));
    }
    
    @Test
    @DisplayName("Actualizar una mesa")
    void updateMesa_Succed() {
    	Mesa mesa = new Mesa("mesa top");
    	Mesa datosNuevos = new Mesa("mesa vip");
    	NewMesa newMesa = new NewMesa(datosNuevos);
    	when(mesaRepository.findById(any())).thenReturn(Optional.of(mesa));
    	mesaService.updateMesa(1L, newMesa, 1L, 1L);
    	assertEquals(datosNuevos.getNombre(), mesa.getNombre());
    	
    	Mesa mesa2 = new Mesa("mesa comun");
    	datosNuevos = new Mesa(null);
    	newMesa = new NewMesa(datosNuevos);
    	when(mesaRepository.findById(any())).thenReturn(Optional.of(mesa2));
    	mesaService.updateMesa(1L, newMesa, 1L, 1L);
    	assertNotEquals(datosNuevos, mesa2);
    }
    
    @Test
    @DisplayName("Actualización fallida, por mesa inexistente")
    void updateMesa_ShouldFailOnNoMesa() {
    	Mesa mesa = new Mesa("mesa comun");
    	NewMesa newMesa = new NewMesa(mesa);
    	when(mesaRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(RuntimeException.class,
    			() -> mesaService.updateMesa(1L, newMesa, 1L, 1L));
    }
    
    // Los testeos de métodos void pueden no hacer uso de asserts
    @Test
    @DisplayName("Eliminar una mesa")
    void deleteMesa_Succed() {
    	Mesa mesa = new Mesa("mesa estandar");
        when(mesaRepository.findById(anyLong())).thenReturn(Optional.of(mesa));

        mesaService.deleteMesa(1L, 1L, 1L);
    }
    
    @Test
    @DisplayName("Eliminar una mesa fallido, por mesa inexistente")
    void deleteMesa_ShouldFailOnNoMesa() {
    	when(mesaRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(RuntimeException.class,
    			() -> mesaService.deleteMesa(1L, 1L, 1L));
	}

    @Test
    @DisplayName("Eliminar una mesa fallido, por estar ocupada")
    void deleteMesa_ShouldFailOnMesaOcupada() {
    	
    	Mesa mesa = new Mesa("mesa estandar");
    	mesa.setMesaOcupada(true); // si la mesa se setea en false (libre): exception
    	
        when(mesaRepository.findById(anyLong())).thenReturn(Optional.of(mesa));
        
    	assertThrows(RuntimeException.class,
    			() -> mesaService.deleteMesa(1L, 1L, 1L));
	}
        
    @Test
    @DisplayName("Creación de un pedido")   
    void createPedido_Succed() {
    	Pedido pedido = new Pedido("happy hour", "2 por 1");
    	Mesa mesa = new Mesa("mesa estandar");
    	when(mesaRepository.findById(any())).thenReturn(Optional.of(mesa));
    	
    	assertTrue(mesa.getPedidos().isEmpty());
    	
    	mesaService.createPedido(new NewPedido(pedido), 1L, 1L, 1L);
    	mesa.addPedido(pedido);
    	
    	assertFalse(mesa.getPedidos().isEmpty());
    }
    
    @Test
    @DisplayName("Creación de un pedido fallido por mesa inexistente")   
    void createPedido_FailedOnNoMesa() {
    	Pedido pedido = new Pedido("happy hour", "2 por 1");
    	
    	when(mesaRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(NoSuchElementException.class,
    			() -> mesaService.createPedido(new NewPedido(pedido), 1L, 1L, 1L));
    }
    
}