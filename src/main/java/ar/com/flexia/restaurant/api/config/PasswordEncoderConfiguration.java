package ar.com.flexia.restaurant.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder(@Value("${security.password.strength:10}") Integer passwordStrength) {
		return new BCryptPasswordEncoder(passwordStrength);
	}
	
}