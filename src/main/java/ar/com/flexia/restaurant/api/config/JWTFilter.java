package ar.com.flexia.restaurant.api.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import ar.com.flexia.restaurant.services.JWTService;

public class JWTFilter extends BasicAuthenticationFilter {
	
	private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

	@Autowired
	private HttpMessageConverter<String> messageConverter;

	@Autowired
	private JWTService jwtService;
	
	public JWTFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authToken = request.getHeader(JWTConfig.HEADER_NAME);
		
		if(ObjectUtils.isEmpty(authToken) || !authToken.startsWith(JWTConfig.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
		} else {
			try {
				DecodedJWT decoded = jwtService.verify(authToken);
				List<SimpleGrantedAuthority> auths = decoded.getClaim(JWTConfig.AUTHORITIES_CLAIM)
						.asList(String.class).stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
			
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decoded.getSubject(),
						null, auths);
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (JWTVerificationException e) {
				LOG.trace("JWT Verification failed", e);
				ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
		        outputMessage.setStatusCode(HttpStatus.FORBIDDEN);
		        messageConverter.write(e.getMessage(), null, outputMessage);
			}
			chain.doFilter(request, response);
		}
	}
	
}