package ar.com.flexia.restaurant.api.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private List<String> corsAllowedOrigins;
	
	
	public SecurityConfig(@Value("#{'${cors.allowed-origins:*}'.split(',')}") List<String> corsAllowedOrigins) {
		super();
		this.corsAllowedOrigins = corsAllowedOrigins;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors()
			.and().csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and().authorizeRequests()
			.antMatchers("/h2", "/h2/*").permitAll()
			.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
			.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
			.antMatchers(HttpMethod.POST, "/user").permitAll()
			.anyRequest().authenticated()
			.and().addFilter(jwtFilter())
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	
	@Bean
	public JWTFilter jwtFilter() throws Exception {
		return new JWTFilter (this.authenticationManager());
	}
	
	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() {
	    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		List<String> allowedMethods = new ArrayList<>();
		allowedMethods.add(CorsConfiguration.ALL);
		CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
		corsConfig.setAllowedMethods(allowedMethods);
		corsConfig.addExposedHeader(JWTConfig.HEADER_NAME);
		corsConfig.setAllowedOrigins(corsAllowedOrigins);
		corsConfig.setAllowCredentials(true);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
}