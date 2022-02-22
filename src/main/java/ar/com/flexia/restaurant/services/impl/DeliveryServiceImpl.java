package ar.com.flexia.restaurant.services.impl;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.DeliveryRepository;
import ar.com.flexia.restaurant.model.repositories.ProductoRepository;
import ar.com.flexia.restaurant.model.repositories.RestaurantRepository;
import ar.com.flexia.restaurant.services.*;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository repo;

    private ProductoRepository productoRepository;

    private RestaurantRepository restaurantRepo;

    private final UserService userService;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository,
                               ProductoRepository productoRepository,
                               RestaurantRepository restaurantRepository,
                               UserService userService) {

        this.repo = deliveryRepository;
        this.productoRepository = productoRepository;
        this.userService = userService;
        this.restaurantRepo = restaurantRepository;
    }

    @Override
    @Transactional
    public Delivery crearDelivery(NewDelivery newDelivery, List<Integer> productList,
    						 Long restaurantId, Long userId) {
        
    	userService.validarOperador(restaurantId, userId);
        
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
        										.orElseThrow(NoSuchElementException::new);
        
        if(newDelivery.getPorcentajeDescuento() < 0 || newDelivery.getPorcentajeDescuento() > 100)
            throw new IllegalArgumentException("Ingrese un descuento entre 0 y 100");
        
        Delivery delivery = new Delivery(newDelivery.getDireccion(),
                					     (long) newDelivery.getTelefono(),
                					     newDelivery.getPorcentajeDescuento(),
        						         restaurant);
        if(productList == null)
            repo.save(delivery);
        
        else{
        
        	List<Producto> productos = new ArrayList<>();
            
        	for (Integer productId: productList) {
             
        		Producto p = productoRepository.findProductoByIdAndRestaurant_Id(productId, restaurantId)
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            
        		if(p.isActivo())
					productos.add(p);
			
        		else
					throw new IllegalArgumentException("Producto " + p.getNombre() + " no disponible.");   
            }
            
            delivery.setProductos(productos);
            
            repo.save(delivery);
            
        }
        return delivery;
    }
    
    @Override
    @Transactional
	public List<Delivery> findDeliveries(Long restaurantId, Long userId) {
    	
    	userService.validarOperador(restaurantId, userId);
    	
    	Restaurant r = restaurantRepo.findById(restaurantId).orElseThrow(NoSuchElementException::new);
    	
		return r.getDeliveries();

    }

    @Override
    @Transactional
    public void aplicarDescuento(long restaurantId, long deliveryId, Double porcentajeDescuento, long userId) {
        userService.validarOperador(restaurantId, userId);

        if(porcentajeDescuento < 0 || porcentajeDescuento > 100)
            throw new IllegalArgumentException("Ingrese un descuento entre 0 y 100");

        Delivery delivery = repo.findById(deliveryId)
        					.orElseThrow(NoSuchElementException::new);

        if(!delivery.isPagado()) {
        	delivery.setPorcentajeDescuento(porcentajeDescuento);
            repo.save(delivery);
        }

        else
            throw new IllegalArgumentException("El delivery ya está pagado.");

    }
    
    @Override
    @Transactional
    public Factura liberarPedido(long restaurantId, long deliveryId, Long userId) {

        userService.validarOperador(restaurantId, userId);

        Delivery delivery = repo.findById(deliveryId)
        		.orElseThrow(() -> new NoSuchElementException("Delivery inextistente"));

        if(!delivery.isPagado()) {

            Factura factura = new Factura();

            factura.setProductos(delivery.getProductos());

            factura.setDescuento(delivery.getPorcentajeDescuento());

            factura.getTotal();

            delivery.setPagado(true);

            repo.save(delivery);

            return factura;

        }

        else
            throw new IllegalArgumentException("El delivery ya está pagado. ");

    }

    @Override
    @Transactional
    public void addProducto(long restaurantId, long deliveryId, List<Integer> productList, long userId) {
        userService.validarOperador(restaurantId, userId);

        Delivery delivery = repo.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el delivery con ese id"));

        List<Producto> productos = new ArrayList<>();
        
        for (Integer productId: productList) {
            Producto p = productoRepository.findProductoByIdAndRestaurant_Id(productId, restaurantId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            productos.add(p);
        }
        
        delivery.setProductos(productos);
        
        repo.save(delivery);

    }

}