package ar.com.flexia.restaurant.services;

import java.util.List;
import java.util.Optional;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.User;


public interface UserService {

	public User createUser(User user);
	
	public Optional<User> findUser(String name);

	public User findById(long userId);
	
	public List<User> findAll();
	
	void deleteUser(long userId);

	public Session login(AdminCredentials credentials);
	
	public void validarOperador(Long restaurantId, Long userId);
	
}