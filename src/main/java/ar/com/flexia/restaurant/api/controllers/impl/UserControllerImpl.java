package ar.com.flexia.restaurant.api.controllers.impl;

import java.util.List;

import ar.com.flexia.restaurant.api.controllers.UserController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.services.*;


@RestController
@RequestMapping(path = "/users")
public class UserControllerImpl implements UserController {

	private UserService service;

	public UserControllerImpl(UserService service) {
		super();
		this.service = service;
	}
	
	@Secured({ "ADMIN" })
	@PostMapping
	public User createUser(User user) {
		return service.createUser(user);
	}

	@Secured({ "ADMIN" })
	@GetMapping(path = "/{userId}")
	public User getUser(@PathVariable("userId") Long userId) {
		return service.findById(userId);
	}
	
	@Secured({ "ADMIN" })
	@GetMapping
	public List<User> getUsers() {
		return service.findAll();
	}
	
	@Secured({"ADMIN"})
	@DeleteMapping(path = "/{userId}")
	public void deleteUser(@PathVariable (value = "userId") Long userId) {
		service.deleteUser(userId);
	}
	
}