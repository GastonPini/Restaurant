package ar.com.flexia.restaurant.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.ProductoRepository;
import ar.com.flexia.restaurant.model.repositories.RestaurantRepository;
import ar.com.flexia.restaurant.services.*;


@Service
public class ProductoServiceImpl implements ProductoService {

	private ProductoRepository repo;
	
	private final RestaurantRepository restaurantRepo;
	
	private UserService userService;
		
	public ProductoServiceImpl(ProductoRepository productoRepo,
							   RestaurantRepository restaurantRepo,
							   UserService userService) {
		super();
		this.repo = productoRepo;
		this.restaurantRepo = restaurantRepo;
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public Producto createProducto(Producto producto, Long restaurantId, Long userId) {
		
		userService.validarOperador(restaurantId, userId);
		
		Optional<Restaurant> restaurant = restaurantRepo.findByIdAndProductos_Nombre(restaurantId, producto.getNombre());
		
		if(restaurant.isPresent())
				throw new IllegalArgumentException("El producto " + producto.getNombre() + " ya existe para " + restaurant.get().getNombre() + ". Ingrese otro nombre.");
			
		else {
			Restaurant r = restaurantRepo.findById(restaurantId).orElseThrow(()
					-> new NoSuchElementException("Restaurant inexistente"));

			producto.setRestaurant(r);
		
			return repo.save(producto);
		}
		
	}
	
	@Override
	@Transactional
	public Producto findById(long productoId, Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		
		return repo.findById(productoId)
				.orElseThrow(() -> new APIException("Producto no encontrado."));
	}
	
	public List<Producto> findAll(Long restaurantId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		
		Restaurant restaurant = restaurantRepo.findById(restaurantId)
						.orElseThrow(NoSuchElementException::new);
		
		return restaurant.getProductos();
	}
	
	@Override
	@Transactional
	public Producto updateProducto(long restaurantId, long productoId, Producto producto,
		   long userId) {
		userService.validarOperador(restaurantId, userId);
		
		Producto p = repo.findById(productoId).orElseThrow(NoSuchElementException::new);
		
		if (null != producto.getNombre())
			p.setNombre(producto.getNombre());
		
		if (0L != producto.getPrecio())
			p.setPrecio(producto.getPrecio());
		
		return repo.save(p);		
	}
	
	@Override
	@Transactional
	public String cambiarEstadoProducto(long restaurantId, long productoId, Long userId) {
		userService.validarOperador(restaurantId, userId);
		
		Producto producto = repo.findById(productoId)
				.orElseThrow(NoSuchElementException::new);
		
		if(producto.isActivo()) {
			producto.setActivo(false);
			return "Producto inhabilitado.";
		}
		else {
			producto.setActivo(true);
			return "Producto habilitado.";
		}
		
	}
	
}