package ar.com.flexia.restaurant.api.controllers.impl;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ar.com.flexia.restaurant.services.*;
import ar.com.flexia.restaurant.model.entity.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerImplTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@Test
	@DisplayName("Obtención de lista de usuarios")
	@WithMockUser(username = "admin", authorities = { "ADMIN" })
	void findAll_Succed() throws Exception {
		Restaurant restaurant1 = new Restaurant("rest1", "rest1");
		Restaurant restaurant2 = new Restaurant("rest2", "rest2");
    	User user1 = new User("Nico", "lala", UserProfile.OPERADOR, restaurant1);
		User user2 = new User("Gaston", "lalala", UserProfile.OPERADOR, restaurant2);
		
		List<User> lista = new ArrayList<User>(Arrays.asList(user1, user2));
		when(userService.findAll()).thenReturn(lista);
		
		assertEquals(2, lista.size());
		
		mockMvc.perform(get("/users").characterEncoding(StandardCharsets.UTF_8)) 
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(user1.getNombre())))
				.andExpect(content().string(containsString(user2.getNombre())));	
	}
	
	@Test
	@DisplayName("Error al obtener lista de usuarios")
	void findAll_ShouldFail() throws Exception{
		mockMvc.perform(get("/users")).andExpect(status().is(403));
	}
	
}