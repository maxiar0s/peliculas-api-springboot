package com.peliculas_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI peliculasOpenApi() {
		return new OpenAPI().info(new Info()
				.title("Peliculas API")
				.description("Microservicio Spring Boot para consultar y administrar peliculas")
				.version("1.0.0")
				.contact(new Contact().name("Peliculas API")));
	}
}
