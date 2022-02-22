package ar.com.flexia.restaurant.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class JWTConfig {
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_NAME = "Authorization";
	public static final String AUTHORITIES_CLAIM = "authorities";
	
	private Algorithm algorithm = null;
	private String secret;
	private Long expiration;
	
	public JWTConfig(@Value("${security.jwt.secret:my-super-default-secret}") String secret,
			@Value("${security.jwt.exp:86400}") Long expiration) {
		this.algorithm = Algorithm.HMAC512(secret);
		this.secret = secret;
		this.expiration = expiration;
	}
	
	public String getSecret() {
		return secret;
	}

	public Long getExpiration() {
		return expiration;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}
	
}