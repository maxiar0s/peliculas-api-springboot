package com.peliculas_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.peliculas_api.model.Pelicula;

@Service
public class PeliculaService {

	private final List<Pelicula> peliculas = List.of(
			new Pelicula(1L, "El Padrino", 1972, "Francis Ford Coppola", "Drama criminal",
					"La familia Corleone enfrenta conflictos internos y externos dentro del crimen organizado en Nueva York."),
			new Pelicula(2L, "Interestelar", 2014, "Christopher Nolan", "Ciencia ficcion",
					"Un grupo de astronautas viaja a traves de un agujero de gusano para buscar un nuevo hogar para la humanidad."),
			new Pelicula(3L, "Parasitos", 2019, "Bong Joon-ho", "Thriller",
					"Dos familias de clases sociales opuestas quedan unidas por una relacion tan oportunista como peligrosa."),
			new Pelicula(4L, "El viaje de Chihiro", 2001, "Hayao Miyazaki", "Animacion",
					"Una nina entra en un mundo espiritual y debe encontrar la manera de rescatar a sus padres y volver a casa."),
			new Pelicula(5L, "La La Land", 2016, "Damien Chazelle", "Musical",
					"Una actriz y un musico luchan por cumplir sus suenos mientras intentan sostener su relacion en Los Angeles."));

	public List<Pelicula> obtenerTodas() {
		return peliculas;
	}

	public Optional<Pelicula> obtenerPorId(Long id) {
		return peliculas.stream()
				.filter(pelicula -> pelicula.getId().equals(id))
				.findFirst();
	}
}
