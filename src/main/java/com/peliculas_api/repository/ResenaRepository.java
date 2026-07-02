package com.peliculas_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peliculas_api.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

	List<Resena> findByPeliculaIdOrderByCreadaEnDesc(Long peliculaId);
}
