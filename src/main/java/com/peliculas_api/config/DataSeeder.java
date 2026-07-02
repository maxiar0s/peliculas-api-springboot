package com.peliculas_api.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.peliculas_api.model.Pelicula;
import com.peliculas_api.repository.PeliculaRepository;

@Component
public class DataSeeder implements CommandLineRunner {

	private final PeliculaRepository peliculaRepository;

	public DataSeeder(PeliculaRepository peliculaRepository) {
		this.peliculaRepository = peliculaRepository;
	}

	@Override
	public void run(String... args) {
		if (peliculaRepository.count() > 0) {
			return;
		}

		peliculaRepository.saveAll(List.of(
				nuevaPelicula("El Padrino", 1972, "Francis Ford Coppola", "Drama criminal",
						"La familia Corleone enfrenta conflictos internos y externos dentro del crimen organizado en Nueva York.",
						"the-godfather", "title/tt0068646", "m/the_godfather"),
				nuevaPelicula("Interestelar", 2014, "Christopher Nolan", "Ciencia ficcion",
						"Un grupo de astronautas viaja a traves de un agujero de gusano para buscar un nuevo hogar para la humanidad.",
						"interstellar", "title/tt0816692", "m/interstellar_2014"),
				nuevaPelicula("Parasitos", 2019, "Bong Joon-ho", "Thriller",
						"Dos familias de clases sociales opuestas quedan unidas por una relacion tan oportunista como peligrosa.",
						"parasite", "title/tt6751668", "m/parasite_2019"),
				nuevaPelicula("El viaje de Chihiro", 2001, "Hayao Miyazaki", "Animacion",
						"Una nina entra en un mundo espiritual y debe encontrar la manera de rescatar a sus padres y volver a casa.",
						"spirited-away", "title/tt0245429", "m/spirited_away"),
				nuevaPelicula("La La Land", 2016, "Damien Chazelle", "Musical",
						"Una actriz y un musico luchan por cumplir sus suenos mientras intentan sostener su relacion en Los Angeles.",
						"la-la-land", "title/tt3783958", "m/la_la_land")));
	}

	private Pelicula nuevaPelicula(String titulo, int anio, String director, String genero, String sinopsis,
			String imagen, String imdbPath, String rottenTomatoesPath) {
		return new Pelicula(null, titulo, anio, director, genero, sinopsis,
				"http://localhost:9000/peliculas-imagenes/" + imagen + ".svg",
				"https://www.imdb.com/" + imdbPath + "/",
				"https://www.rottentomatoes.com/" + rottenTomatoesPath);
	}
}
