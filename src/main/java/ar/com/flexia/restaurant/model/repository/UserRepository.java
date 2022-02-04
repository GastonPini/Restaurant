package ar.com.flexia.restaurant.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.flexia.restaurant.model.entity.*;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByNombre(String nombre);

}