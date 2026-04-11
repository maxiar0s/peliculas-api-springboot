package com.peliculas_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peliculas_api.model.Pelicula;
import com.peliculas_api.service.PeliculaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

	private final PeliculaService peliculaService;

	public PeliculaController(PeliculaService peliculaService) {
		this.peliculaService = peliculaService;
	}

	@GetMapping
	public List<Pelicula> obtenerPeliculas() {
		return peliculaService.obtenerTodas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPeliculaPorId(@PathVariable Long id) {
		return peliculaService.obtenerPorId(id)
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("mensaje", "No se encontro una pelicula con id " + id)));
	}

	@PostMapping
	public ResponseEntity<Pelicula> crearPelicula(@RequestBody Pelicula pelicula) {
		Pelicula peliculaCreada = peliculaService.crear(pelicula);
		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, "/peliculas/" + peliculaCreada.getId())
				.body(peliculaCreada);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
		return peliculaService.actualizar(id, pelicula)
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("mensaje", "No se encontro una pelicula con id " + id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarPelicula(@PathVariable Long id) {
		if (peliculaService.eliminar(id)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("mensaje", "No se encontro una pelicula con id " + id));
	}
}
