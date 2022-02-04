package ar.com.flexia.restaurant.api.controllers.impl;

import ar.com.flexia.restaurant.api.controllers.AuthController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.services.UserService;

@RestController
@RequestMapping(path = "/auth")
public class AuthControllerImpl implements AuthController {

	private UserService service;
	
	public AuthControllerImpl(UserService userService) {
		super();
		this.service = userService;
	}

	@PostMapping(path = "/login")
	public Session login(@RequestBody AdminCredentials creds) {
		return service.login(creds);
	}
	
}