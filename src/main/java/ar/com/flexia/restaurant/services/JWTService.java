package ar.com.flexia.restaurant.services;

import com.auth0.jwt.interfaces.DecodedJWT;

import ar.com.flexia.restaurant.model.entity.User;


public interface JWTService {
	
	public String issueToken(User user);
	
	public DecodedJWT verify(String jwt);
	
}