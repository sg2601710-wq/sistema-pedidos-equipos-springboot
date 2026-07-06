package com.sgonzalez.pedido_equipos_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipos")
public class Equipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "codigoInventario", nullable = false, unique = true)
	private String codigoInventario;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "categoria", nullable = false)
	private String categoria;

	@ManyToOne(optional = false)
	@JoinColumn(name = "estado", nullable = false)
	private Estado estado;

	@Column(name = "ubicacion", nullable = false)
	private String ubicacion;

	@Column(name = "requiereAutorizacion", nullable = false)
	private Boolean requiereAutorizacion = true;

	public Equipo() {
	}

	public Equipo(
			String codigoInventario,
			String nombre,
			String categoria,
			Estado estado,
			String ubicacion,
			Boolean requiereAutorizacion
	) {
		this.codigoInventario = codigoInventario;
		this.nombre = nombre;
		this.categoria = categoria;
		this.estado = estado;
		this.ubicacion = ubicacion;
		this.requiereAutorizacion = requiereAutorizacion;
	}

	@PrePersist
	void applyDefaults() {
		if (requiereAutorizacion == null) {
			requiereAutorizacion = true;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoInventario() {
		return codigoInventario;
	}

	public void setCodigoInventario(String codigoInventario) {
		this.codigoInventario = codigoInventario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Boolean getRequiereAutorizacion() {
		return requiereAutorizacion;
	}

	public void setRequiereAutorizacion(Boolean requiereAutorizacion) {
		this.requiereAutorizacion = requiereAutorizacion;
	}
}
