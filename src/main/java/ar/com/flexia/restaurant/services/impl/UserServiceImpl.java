package ar.com.flexia.restaurant.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.flexia.restaurant.api.dto.*;
import ar.com.flexia.restaurant.model.entity.*;
import ar.com.flexia.restaurant.model.repositories.UserRepository;
import ar.com.flexia.restaurant.services.*;


@Service
public class UserServiceImpl implements UserService {

	private PasswordEncoder encoder;
	
	private UserRepository repo;
	
	private JWTService jwtService;
	
	public UserServiceImpl(PasswordEncoder encoder, JWTService jwt, UserRepository user) {
		super();
		this.encoder = encoder;
		this.jwtService = jwt;
		this.repo = user;
		
		if (0 == this.repo.count())
			repo.save(new User("admin", encoder.encode("admin"), UserProfile.ADMIN));
	}
	
	@Override
	@Transactional
	public User createUser(User user) {
		
		if(repo.findByNombre(user.getNombre()).isPresent())
			throw new IllegalArgumentException("El usuario " + user.getNombre() + " ya existe, ingrese otro nombre.");
		
		return repo.save(new User(user.getNombre(), encoder.encode(user.getPassword()), UserProfile.OPERADOR, user.getRestaurant()));
	}
	
	@Override
	@Transactional
	public Optional<User> findUser(String nombre) {
		return repo.findByNombre(nombre);
	}
	
	@Override
	@Transactional
	public User findById(long userId) {
		return repo.findById(userId)
				.orElseThrow(() -> new APIException("Usuario no encontrado,"));
	}
	
	@Override
	@Transactional
	public List<User> findAll() {
		return repo.findAll();
	}
	
	@Override
	@Transactional
	public void deleteUser(long userId) {
		repo.deleteById(userId);
	}
	
	@Override
	@Transactional
	public Session login(AdminCredentials credentials) {
		User user = repo.findByNombre(credentials.getNombre())
				.orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));
		
		if (!encoder.matches(credentials.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("Credenciales inválidas");
		}
		
		String token = jwtService.issueToken(user);
		return new Session(token, user);
	}
	
	@Override
	@Transactional
	public void validarOperador(Long restaurantId, Long userId) {
		User u = repo.findById(userId).orElseThrow(NoSuchElementException::new);
		if(!restaurantId.equals(u.getRestaurant().getId()))
			throw new IllegalArgumentException("Restaurant restringido.");
	}
	
}