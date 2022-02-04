package ar.com.flexia.restaurant.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfiguraton {

    @Bean
    public OpenAPI customOpenAPI() {

        Contact contact = new Contact()
        		.name("Nicolás Corvi & Gastón Pini")
        		.email("gaston.pini@flexia.com.ar")
                .url("https://flexia.com.ar");

        Info info = new Info()
        		.title("Restaurant")
        		.description("Capacitación Flexia")
        		.version("v1.0")
        		.contact(contact);

        Components components = new Components();

        return new OpenAPI().info(info).components(components);
    }
    
}