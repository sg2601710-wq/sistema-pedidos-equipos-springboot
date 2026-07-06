package com.sgonzalez.pedido_equipos_api.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;

	@Column(name = "email", nullable = false, unique = true, length = 255)
	private String email;

	@Column(name = "contrasenaHash", nullable = false, length = 255)
	private String contrasenaHash;

	@ManyToOne(optional = false)
	@JoinColumn(name = "rol", nullable = false)
	private Rol rol;

	@Column(name = "activo", nullable = false)
	private Boolean activo = true;

	@OneToMany(mappedBy = "solicitante")
	private List<Solicitud> solicitudes = new ArrayList<>();

	public Usuario() {
	}

	public Usuario(String nombre, String email, String contrasenaHash, Rol rol, Boolean activo) {
		this.nombre = nombre;
		this.email = email;
		this.contrasenaHash = contrasenaHash;
		this.rol = rol;
		this.activo = activo;
	}

	@PrePersist
	void applyDefaults() {
		if (activo == null) {
			activo = true;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenaHash() {
		return contrasenaHash;
	}

	public void setContrasenaHash(String contrasenaHash) {
		this.contrasenaHash = contrasenaHash;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}
}
