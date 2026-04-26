package com.peliculas_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PeliculaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deberiaRetornarTodasLasPeliculas() throws Exception {
		mockMvc.perform(get("/peliculas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.peliculas.length()").value(5))
				.andExpect(jsonPath("$._embedded.peliculas[0].titulo").value("El Padrino"))
				.andExpect(jsonPath("$._embedded.peliculas[0]._links.self.href").exists())
				.andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	void deberiaRetornarUnaPeliculaPorId() throws Exception {
		mockMvc.perform(get("/peliculas/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.titulo").value("Interestelar"))
				.andExpect(jsonPath("$._links.self.href").exists())
				.andExpect(jsonPath("$._links.peliculas.href").exists());
	}

	@Test
	void deberiaRetornarNotFoundCuandoNoExisteElId() throws Exception {
		mockMvc.perform(get("/peliculas/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.mensaje").value("No se encontro una pelicula con id 99"));
	}
}
