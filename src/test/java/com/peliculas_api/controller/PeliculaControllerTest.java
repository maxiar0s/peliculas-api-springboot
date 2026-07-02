package com.peliculas_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
				.andExpect(jsonPath("$._embedded.peliculas[0].imagenUrl").exists())
				.andExpect(jsonPath("$._embedded.peliculas[0].imdbUrl").value("https://www.imdb.com/title/tt0068646/"))
				.andExpect(jsonPath("$._embedded.peliculas[0].rottenTomatoesUrl").value("https://www.rottentomatoes.com/m/the_godfather"))
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

	@Test
	void deberiaCrearYListarResenas() throws Exception {
		mockMvc.perform(post("/peliculas/1/resenas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"autor\":\"Ana\",\"puntuacion\":5,\"comentario\":\"Excelente clasico\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.peliculaId").value(1))
				.andExpect(jsonPath("$.autor").value("Ana"))
				.andExpect(jsonPath("$.puntuacion").value(5));

		mockMvc.perform(get("/peliculas/1/resenas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].comentario").value("Excelente clasico"));
	}

	@Test
	void deberiaExponerOpenApiJson() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.info.title").value("Peliculas API"))
				.andExpect(jsonPath("$.paths['/peliculas'].get.summary").value("Listar peliculas"));
	}
}
