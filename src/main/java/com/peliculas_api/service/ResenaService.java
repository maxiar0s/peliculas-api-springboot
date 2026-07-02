package com.peliculas_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.peliculas_api.model.Resena;
import com.peliculas_api.repository.PeliculaRepository;
import com.peliculas_api.repository.ResenaRepository;

@Service
public class ResenaService {

	private final ResenaRepository resenaRepository;
	private final PeliculaRepository peliculaRepository;

	public ResenaService(ResenaRepository resenaRepository, PeliculaRepository peliculaRepository) {
		this.resenaRepository = resenaRepository;
		this.peliculaRepository = peliculaRepository;
	}

	public List<Resena> obtenerPorPelicula(Long peliculaId) {
		return resenaRepository.findByPeliculaIdOrderByCreadaEnDesc(peliculaId);
	}

	public Optional<Resena> crear(Long peliculaId, Resena resena) {
		if (!peliculaRepository.existsById(peliculaId)) {
			return Optional.empty();
		}

		resena.setId(null);
		resena.setPeliculaId(peliculaId);
		resena.setAutor(normalizarTexto(resena.getAutor(), "Anonimo"));
		resena.setComentario(normalizarTexto(resena.getComentario(), "Sin comentario"));
		resena.setPuntuacion(Math.max(1, Math.min(5, resena.getPuntuacion())));
		return Optional.of(resenaRepository.save(resena));
	}

	private String normalizarTexto(String texto, String valorPorDefecto) {
		if (texto == null || texto.isBlank()) {
			return valorPorDefecto;
		}

		return texto.trim();
	}
}
