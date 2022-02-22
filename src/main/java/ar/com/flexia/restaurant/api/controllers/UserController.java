package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name = "Usuario", description = "Controlador de usuarios")
public interface UserController {
	
	@Operation(summary = "Crear un nuevo usuario",
            description = "Acción realizada por el admin")
    public User createUser(User user);

	@Operation(summary = "Obtener un determinado usuario",
            description = "Acción realizada por el admin")
    public User getUser(Long userId);

	@Operation(summary = "Obtener la lista de usuarios",
            description = "Acción realizada por el admin")
    public List<User> getUsers();

	@Operation(summary = "Eliminar un determinado usuario",
            description = "Acción realizada por el admin")
    public void deleteUser(Long userId);

}