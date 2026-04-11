package com.peliculas_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peliculas_api.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
	List<Pelicula> findAllByOrderByIdAsc();
}
