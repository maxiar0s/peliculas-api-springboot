package com.peliculas_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.peliculas_api.service.PeliculaService;

@WebMvcTest(PeliculaController.class)
@Import(PeliculaService.class)
class PeliculaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deberiaRetornarTodasLasPeliculas() throws Exception {
		mockMvc.perform(get("/peliculas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(5))
				.andExpect(jsonPath("$[0].titulo").value("El Padrino"));
	}

	@Test
	void deberiaRetornarUnaPeliculaPorId() throws Exception {
		mockMvc.perform(get("/peliculas/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.titulo").value("Interestelar"));
	}

	@Test
	void deberiaRetornarNotFoundCuandoNoExisteElId() throws Exception {
		mockMvc.perform(get("/peliculas/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.mensaje").value("No se encontro una pelicula con id 99"));
	}
}
