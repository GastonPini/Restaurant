package ar.com.flexia.restaurant.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repository.*;
import ar.com.flexia.restaurant.services.*;


@Service
public class PedidoServiceImpl implements PedidoService {
	
	private final PedidoRepository repo;
	
	private final MesaRepository mesaRepo;
	
	private final RestaurantRepository restaurantRepo;
	
	private final ProductoRepository productoRepo;
	
	private UserService userService;
	
	public PedidoServiceImpl(PedidoRepository repo, MesaRepository mesaRepo,
			RestaurantRepository restaurantRepo, ProductoRepository productoRepo,
			UserService userService) {
		super();
		this.repo = repo;
		this.mesaRepo = mesaRepo;
		this.restaurantRepo = restaurantRepo;
		this.productoRepo = productoRepo;
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public Pedido createPedido(NewPedido newPedido, Long mesaId, Long restaurantId,
							   List<Integer>productos, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		if(newPedido.getPorcentajeDescuento() < 0 || newPedido.getPorcentajeDescuento() > 100)
            throw new IllegalArgumentException("Ingrese un descuento entre 0 y 100");
		
		Mesa mesa = mesaRepo.findById(mesaId).orElseThrow(NoSuchElementException::new);

		if(mesa.isMesaOcupada())
			throw new IllegalArgumentException("La mesa tiene un pedido activo.");
		
		Optional<Pedido> pedido = repo.findPedidoByNombreAndMesa_Id(newPedido.getNombre(), mesaId);

		if (pedido.isEmpty()) {
			mesa.setMesaOcupada(true);
			Pedido p = new Pedido(newPedido.getNombre(), newPedido.getDescripcion(), newPedido.getPorcentajeDescuento());

			if(productos != null) {
				List<Producto> prods = new ArrayList<>();
				for (Integer productId : productos) {
					Producto prod = productoRepo.findProductoByIdAndRestaurant_Id(productId, restaurantId)
							.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
					if(prod.isActivo())
						prods.add(prod);
					else
						throw new IllegalArgumentException("Producto " + prod.getNombre() + " no disponible.");
				}

				p.setProductos(prods);
			}

			p.setMesa(mesa);
			
			return repo.save(p);

		}
			
		else
			throw new IllegalArgumentException("El pedido ya existe.");
		
	}

	@Override
	@Transactional
	public List<Pedido> findAll(Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		return repo.findAll();
	}
	
	@Override
	@Transactional
	public Pedido findById(long pedidoId, Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		return repo.findById(pedidoId)
				.orElseThrow(() -> new APIException("Pedido no encontrado"));
	}
	
	@Override
	@Transactional
	public void addProducto(long restaurantId, long pedidoId, long productoId, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		Optional<Pedido> pedido = repo.findById(pedidoId);
		
		Optional<Producto> producto = productoRepo.findProductoByIdAndRestaurant_Id(productoId, restaurantId);
		
		if(pedido.isPresent()) {
		
			if(producto.isPresent()) {
				if(producto.get().isActivo())
					pedido.get().addProducto(producto.get());
				else
					throw new IllegalArgumentException("Producto " + producto.get().getNombre() + " no disponible.");
			}
			
			else
				throw new IllegalArgumentException("Error. Ingrese otros valores.");
			
		}
		else
			throw new IllegalArgumentException("El pedido no existe.");
		
	}			
	

	@Override
	@Transactional
	public List<Producto> getProductos(long pedidoId, Long restaurantId, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		Pedido pedido = repo.findById(pedidoId).orElseThrow(() -> new Error("Pedido no encontrado."));
		
		return pedido.getProductos();			
	
	}
	
	@Override
	public void editarPedido(long restaurantId, long pedidoId, NewPedido pedido, long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		Pedido p = repo.findById(pedidoId).orElseThrow(NoSuchElementException::new);
		
		if(p.isPagado())
			throw new IllegalArgumentException("Pedido ya pagado.");
		
		if(pedido.getPorcentajeDescuento() < 0 || pedido.getPorcentajeDescuento() > 100)
            throw new IllegalArgumentException("Ingrese un descuento entre 0 y 100");
		
		if (null != pedido.getNombre())
			p.setNombre(pedido.getNombre());
		
		if (null != pedido.getDescripcion())
			p.setDescripcion(pedido.getDescripcion());
		
		if (null != pedido.getPorcentajeDescuento() && 0.0 != pedido.getPorcentajeDescuento())
			p.setPorcentajeDescuento(pedido.getPorcentajeDescuento());
		
		repo.save(p);	
		
	}

	@Override
	@Transactional
	public Factura liberarPedido(long restaurantId, long mesaId, long pedidoId, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(NoSuchElementException::new);
		
		Mesa mesa = mesaRepo.findByIdAndRestaurant_Nombre(mesaId, restaurant.getNombre()).orElseThrow(NoSuchElementException::new);
		
		Pedido pedido = repo.findByIdAndMesa_Id(pedidoId, mesaId).orElseThrow(NoSuchElementException::new);
		
		if(!pedido.isPagado()) {
			
			Factura factura = new Factura();
			
			factura.setProductos(pedido.getProductos());

			factura.setDescuento(pedido.getPorcentajeDescuento());

			factura.getTotal();

			pedido.setPagado(true);
					
			mesa.setMesaOcupada(false);
			
			return factura;
				
		}
		
		else
			throw new IllegalArgumentException("El pedido ya está pagado. " + mesa.getNombre() + " libre.");
		
	}

	@Override
	@Transactional
	public void aplicarDescuento(long restaurantId, long mesaId, long pedidoId, Double porcentajeDescuento, long userId) {
		userService.validarOperador(restaurantId, userId);

		if(porcentajeDescuento < 0 || porcentajeDescuento > 100)
            throw new IllegalArgumentException("Ingrese un descuento entre 0 y 100");

		Pedido pedido = repo.findByIdAndMesa_Id(pedidoId, mesaId).orElseThrow(NoSuchElementException::new);

		if(!pedido.isPagado()) {
			pedido.setPorcentajeDescuento(porcentajeDescuento);
			repo.save(pedido);
		}

		else
			throw new IllegalArgumentException("El pedido ya está pagado.");

	}

}