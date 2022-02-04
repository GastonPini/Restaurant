# Restaurant

Aplicación para la gestión de restaurants, sus respectivas mesas, pedidos y productos.

### Entidades

* User: La creación de usuarios, se realiza en conjunto con la creación de su correspondiente restaurant.  
Cada usuario puede administrar solo su restaurant.


* Restaurant: La creación de un restaurant solo la puede realizar el admin del sistema, en la ruta:  
@PostMapping(path = "/restaurant")


* Mesa: La creación de una mesa para un restaurant solo la puede realizar el operador de dicho restaurant, en la ruta:  
@PostMapping(path = "/restaurant/{restaurantId}/mesas/{mesaId}")  
Cada mesa debe estar asociada a un restaurant.  
Dentro de un restaurant, no pueden haber dos mesas con el mismo nombre.  
Cada mesa solo puede poseer un pedido activo. Para liberar una mesa, hay que pagar su pedido, en la ruta:  
@PutMapping(path = "restaurant/{restaurantId}/pedidos/{pedidoId}")


* Pedido: La creación de un pedido para una mesa solo la puede realizar el operador de dicho restaurant, en la ruta:  
@PostMapping(path = "/restaurant/{restaurantId}/mesas/{mesaId}/pedidos")  
Cada pedido debe estar asociado a una mesa.  
Cada mesa, solo puede poseer un pedido activo.


* Delivery: La creación de un pedido de delivery solo la puede realizar el operador de dicho restaurant, en la ruta:  
@PutMapping(path = "restaurant/{restaurantId}/delivery/{deliveryId}")


* Producto: La creación de un producto para un restaurant, solo la puede realizar el operador de dicho restaurant, en la ruta:
@PostMapping(path = "/restaurant/{restaurantId}/productos")
- A cada pedido y cada pedido de delivery, solo se le pueden agregar productos existentes en su correspondiente restaurant.  
  
Es posible ofrecer descuentos a los pedidos, con las siguientes rutas: 

Para pedidos:  
@PutMapping(path = "restaurant/{restaurantId}/mesas/{mesaId}/pedidos/{pedidoId}/descuento/{porcentajeDescuento}")

Para deliveries:  
@PutMapping(path = "restaurant/{restaurantId}/delivery/{deliveryId}/descuento/{porcentajeDescuento}")
