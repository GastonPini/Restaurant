package ar.com.flexia.restaurant.services;

import java.util.List;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;

public interface MesaService {


	public List<Mesa> findAll(Long restaurantId, Long userId);

	Mesa updateMesa(long mesaId, NewMesa newMesa, Long restaurantId, Long userId);
	
	public void deleteMesa(long mesaId, Long restaurantId, Long userId);
	
	public void createPedido(NewPedido newPedido, Long mesaId, Long restaurantId, Long userId);

	public Pedido mostrarPedidoActivo(Long restaurantId, long mesaId, long parseLong);

}