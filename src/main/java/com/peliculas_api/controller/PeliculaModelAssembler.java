package com.peliculas_api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.peliculas_api.model.Pelicula;

@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>> {

	@Override
	public EntityModel<Pelicula> toModel(Pelicula pelicula) {
		return EntityModel.of(pelicula,
				linkTo(methodOn(PeliculaController.class).obtenerPeliculaPorId(pelicula.getId())).withSelfRel(),
				linkTo(methodOn(PeliculaController.class).obtenerPeliculas()).withRel("peliculas"));
	}
}
