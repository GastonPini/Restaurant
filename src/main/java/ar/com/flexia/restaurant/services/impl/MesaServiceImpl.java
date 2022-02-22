package ar.com.flexia.restaurant.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.MesaRepository;
import ar.com.flexia.restaurant.model.repositories.PedidoRepository;
import ar.com.flexia.restaurant.services.*;


@Service
public class MesaServiceImpl implements MesaService {
	
	private final MesaRepository repo;
	
	private final PedidoRepository pedidoRepo;
	
	private final UserService userService;

	public MesaServiceImpl(MesaRepository repo, PedidoRepository pedidoRepo,
						   UserService userService) {
		super();
		this.repo = repo;
		this.pedidoRepo = pedidoRepo;
		this.userService = userService;
	}

	@Override
	@Transactional
	public List<Mesa> findAll(Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
	
		return repo.findAll();
	}
	
	@Override
	public Pedido mostrarPedidoActivo(Long restaurantId, long mesaId, long userId) {
		userService.validarOperador(restaurantId, userId);
	
		return pedidoRepo.findPedidoByPagadoIsFalseAndMesa_Id(mesaId)
						 .orElseThrow(NoSuchElementException::new);
	}
	
	@Override
	@Transactional
	public Mesa updateMesa(long mesaId, NewMesa newMesa, Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		
		Mesa mesa = repo.findById(mesaId).orElseThrow(NoSuchElementException::new);
		
		if (null != newMesa.getNombre())
			mesa.setNombre(newMesa.getNombre());
		
		return repo.save(mesa);		
	}
	
	@Override
	@Transactional
	public void deleteMesa(long mesaId, Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		
		Mesa m = repo.findById(mesaId)
				.orElseThrow(() -> new NoSuchElementException("Mesa inexistente."));
		
		if(m.isMesaOcupada())
			throw new IllegalArgumentException("La mesa se encuentra ocupada, no se puede borrar.");
		
		repo.deleteById(mesaId);
		
	}

	@Override
	@Transactional
	public void createPedido(NewPedido newPedido, Long mesaId, Long restaurantId, Long userId) {

		userService.validarOperador(restaurantId, userId);
		
		Mesa mesa = repo.findById(mesaId).orElseThrow(NoSuchElementException::new);
		
		Pedido pedido = new Pedido(newPedido.getNombre(), newPedido.getDescripcion());
	
		mesa.addPedido(pedido);
	
		repo.save(mesa);
		
	}
	
}