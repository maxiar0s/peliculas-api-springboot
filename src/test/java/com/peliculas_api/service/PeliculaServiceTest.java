package com.peliculas_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.peliculas_api.model.Pelicula;
import com.peliculas_api.repository.PeliculaRepository;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

	@Mock
	private PeliculaRepository peliculaRepository;

	@InjectMocks
	private PeliculaService peliculaService;

	private Pelicula pelicula;

	@BeforeEach
	void setUp() {
		pelicula = new Pelicula(1L, "Matrix", 1999, "Lana Wachowski", "Ciencia ficcion",
				"Un programador descubre la verdadera naturaleza de su realidad.");
	}

	@Test
	void deberiaCrearPeliculaConIdNuloAntesDeGuardar() {
		Pelicula nuevaPelicula = new Pelicula(99L, "Origen", 2010, "Christopher Nolan", "Ciencia ficcion",
				"Un ladron roba secretos dentro de los suenos.");

		when(peliculaRepository.save(any(Pelicula.class))).thenAnswer(invocation -> {
			Pelicula peliculaGuardada = invocation.getArgument(0);
			peliculaGuardada.setId(10L);
			return peliculaGuardada;
		});

		Pelicula resultado = peliculaService.crear(nuevaPelicula);

		assertNotNull(resultado.getId());
		assertEquals(10L, resultado.getId());
		assertEquals("Origen", resultado.getTitulo());
		verify(peliculaRepository, times(1)).save(any(Pelicula.class));
	}

	@Test
	void deberiaActualizarPeliculaCuandoExiste() {
		Pelicula cambios = new Pelicula(null, "Matrix Recargado", 2003, "Lana Wachowski", "Accion",
				"Neo enfrenta una nueva amenaza.");

		when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));
		when(peliculaRepository.save(any(Pelicula.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Optional<Pelicula> resultado = peliculaService.actualizar(1L, cambios);

		assertTrue(resultado.isPresent());
		assertEquals("Matrix Recargado", resultado.get().getTitulo());
		assertEquals(2003, resultado.get().getAnio());
		assertEquals("Accion", resultado.get().getGenero());
		verify(peliculaRepository, times(1)).findById(1L);
		verify(peliculaRepository, times(1)).save(any(Pelicula.class));
	}

	@Test
	void deberiaRetornarFalseCuandoIntentaEliminarYNoExiste() {
		when(peliculaRepository.existsById(7L)).thenReturn(false);

		boolean resultado = peliculaService.eliminar(7L);

		assertFalse(resultado);
		verify(peliculaRepository, times(1)).existsById(7L);
	}
}
