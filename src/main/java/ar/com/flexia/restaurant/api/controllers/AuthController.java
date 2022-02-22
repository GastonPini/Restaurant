package ar.com.flexia.restaurant.api.controllers;

import ar.com.flexia.restaurant.api.dto.AdminCredentials;
import ar.com.flexia.restaurant.api.dto.Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Autorización", description = "API de loggeo")
public interface AuthController {
	
	@Operation(summary = "Login",
            description = "Ruta que devuelve una sesión que contiene el token jwt con las credenciales")
    public Session login(AdminCredentials creds);
	
}