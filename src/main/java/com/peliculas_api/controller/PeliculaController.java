package com.peliculas_api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/peliculas")
@Tag(name = "Peliculas", description = "Endpoints para consultar y administrar peliculas")
public class PeliculaController {

	private final PeliculaService peliculaService;
	private final PeliculaModelAssembler peliculaModelAssembler;

	public PeliculaController(PeliculaService peliculaService, PeliculaModelAssembler peliculaModelAssembler) {
		this.peliculaService = peliculaService;
		this.peliculaModelAssembler = peliculaModelAssembler;
	}

	@Operation(summary = "Listar peliculas", description = "Obtiene todas las peliculas registradas")
	@ApiResponse(responseCode = "200", description = "Peliculas obtenidas correctamente")
	@GetMapping
	public CollectionModel<EntityModel<Pelicula>> obtenerPeliculas() {
		List<EntityModel<Pelicula>> peliculas = peliculaService.obtenerTodas().stream()
				.map(peliculaModelAssembler::toModel)
				.toList();

		return CollectionModel.of(peliculas,
				linkTo(methodOn(PeliculaController.class).obtenerPeliculas()).withSelfRel());
	}

	@Operation(summary = "Buscar pelicula por id", description = "Obtiene una pelicula especifica a partir de su identificador")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Pelicula encontrada"),
			@ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = Map.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPeliculaPorId(@PathVariable Long id) {
		return peliculaService.obtenerPorId(id)
				.<ResponseEntity<?>>map(pelicula -> ResponseEntity.ok(peliculaModelAssembler.toModel(pelicula)))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("mensaje", "No se encontro una pelicula con id " + id)));
	}

	@Operation(summary = "Crear pelicula", description = "Registra una nueva pelicula en el sistema")
	@ApiResponse(responseCode = "201", description = "Pelicula creada correctamente")
	@PostMapping
	public ResponseEntity<EntityModel<Pelicula>> crearPelicula(@RequestBody Pelicula pelicula) {
		Pelicula peliculaCreada = peliculaService.crear(pelicula);
		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, "/peliculas/" + peliculaCreada.getId())
				.body(peliculaModelAssembler.toModel(peliculaCreada));
	}

	@Operation(summary = "Actualizar pelicula", description = "Actualiza los datos de una pelicula existente")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Pelicula actualizada correctamente"),
			@ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = Map.class))) })
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
		return peliculaService.actualizar(id, pelicula)
				.<ResponseEntity<?>>map(peliculaActualizada -> ResponseEntity.ok(peliculaModelAssembler.toModel(peliculaActualizada)))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("mensaje", "No se encontro una pelicula con id " + id)));
	}

	@Operation(summary = "Eliminar pelicula", description = "Elimina una pelicula por su identificador")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pelicula eliminada correctamente"),
			@ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = Map.class))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarPelicula(@PathVariable Long id) {
		if (peliculaService.eliminar(id)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("mensaje", "No se encontro una pelicula con id " + id));
	}
}
