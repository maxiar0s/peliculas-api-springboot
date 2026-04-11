package com.peliculas_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.peliculas_api.model.Pelicula;
import com.peliculas_api.repository.PeliculaRepository;

@Service
public class PeliculaService {

	private final PeliculaRepository peliculaRepository;

	public PeliculaService(PeliculaRepository peliculaRepository) {
		this.peliculaRepository = peliculaRepository;
	}

	public List<Pelicula> obtenerTodas() {
		return peliculaRepository.findAllByOrderByIdAsc();
	}

	public Optional<Pelicula> obtenerPorId(Long id) {
		return peliculaRepository.findById(id);
	}

	public Pelicula crear(Pelicula pelicula) {
		pelicula.setId(null);
		return peliculaRepository.save(pelicula);
	}

	public Optional<Pelicula> actualizar(Long id, Pelicula peliculaActualizada) {
		return peliculaRepository.findById(id)
				.map(peliculaExistente -> {
					peliculaExistente.setTitulo(peliculaActualizada.getTitulo());
					peliculaExistente.setAnio(peliculaActualizada.getAnio());
					peliculaExistente.setDirector(peliculaActualizada.getDirector());
					peliculaExistente.setGenero(peliculaActualizada.getGenero());
					peliculaExistente.setSinopsis(peliculaActualizada.getSinopsis());
					return peliculaRepository.save(peliculaExistente);
				});
	}

	public boolean eliminar(Long id) {
		if (!peliculaRepository.existsById(id)) {
			return false;
		}

		peliculaRepository.deleteById(id);
		return true;
	}
}
