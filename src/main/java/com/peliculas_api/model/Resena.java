package com.peliculas_api.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "resenas")
public class Resena {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "pelicula_id", nullable = false)
	private Long peliculaId;

	@Column(nullable = false, length = 80)
	private String autor;

	@Column(nullable = false)
	private int puntuacion;

	@Column(nullable = false, length = 1000)
	private String comentario;

	@Column(name = "creada_en", nullable = false, updatable = false)
	private Instant creadaEn;

	public Resena() {
	}

	public Resena(Long id, Long peliculaId, String autor, int puntuacion, String comentario, Instant creadaEn) {
		this.id = id;
		this.peliculaId = peliculaId;
		this.autor = autor;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.creadaEn = creadaEn;
	}

	@PrePersist
	void prePersist() {
		if (creadaEn == null) {
			creadaEn = Instant.now();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPeliculaId() {
		return peliculaId;
	}

	public void setPeliculaId(Long peliculaId) {
		this.peliculaId = peliculaId;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Instant getCreadaEn() {
		return creadaEn;
	}

	public void setCreadaEn(Instant creadaEn) {
		this.creadaEn = creadaEn;
	}
}
