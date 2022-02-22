package ar.com.flexia.restaurant.api.controllers.impl;

import ar.com.flexia.restaurant.api.controllers.DeliveryController;
import ar.com.flexia.restaurant.api.dto.Factura;
import ar.com.flexia.restaurant.api.dto.NewDelivery;
import ar.com.flexia.restaurant.model.entity.Delivery;
import ar.com.flexia.restaurant.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DeliveryControllerImpl implements DeliveryController {

    @Autowired
    private DeliveryService service;

    @Secured({ "OPERADOR" })
    @PostMapping("/restaurant/{restaurantId}/delivery")
    public Delivery crearDelivery(@RequestBody NewDelivery request,
                              	  @PathVariable("restaurantId") Long restaurantId,
                              	  @RequestParam(name = "productos", required = false) List<Integer> productList,
                              	  Authentication auth){
        return service.crearDelivery(request, productList, restaurantId,
        							 Long.parseLong(auth.getName()));
    }
    
    @Secured({ "OPERADOR" })
    @GetMapping("/restaurant/{restaurantId}/delivery")
    public List<Delivery> getPedidosDelivery(@PathVariable("restaurantId") Long restaurantId,
 		   									Authentication auth){
 	   return service.findDeliveries(restaurantId, Long.parseLong(auth.getName()));
    }
    
    @Secured({ "OPERADOR" })
    @PutMapping(path = "restaurant/{restaurantId}/delivery/{deliveryId}/productos")
    public void addProducto(@PathVariable("restaurantId") long restaurantId,
                            @PathVariable("deliveryId") long deliveryId,
                            @RequestParam(name = "productos", required = true) List<Integer> productList,
                            Authentication auth) {
        service.addProducto(restaurantId, deliveryId, productList,
        					Long.parseLong(auth.getName()));
    }
    
    @Secured({ "OPERADOR" })
    @PutMapping(path = "restaurant/{restaurantId}/delivery/{deliveryId}/descuento/{porcentajeDescuento}")
    public void aplicarDescuento(@PathVariable("restaurantId") long restaurantId,
                                 @PathVariable("deliveryId") long deliveryId,
                                 @PathVariable("porcentajeDescuento") Double porcentajeDescuento,
                                 Authentication auth){
        service.aplicarDescuento(restaurantId, deliveryId, porcentajeDescuento,
        						 Long.parseLong(auth.getName()));
    }

    @Secured({ "OPERADOR" })
    @PutMapping(path = "restaurant/{restaurantId}/delivery/{deliveryId}")
    public Factura liberarPedido(@PathVariable("restaurantId") long restaurantId,
                                 @PathVariable("deliveryId") long deliveryId,
                                 Authentication auth) {
        return service.liberarPedido(restaurantId, deliveryId,
        							 Long.parseLong(auth.getName()));
    }

}