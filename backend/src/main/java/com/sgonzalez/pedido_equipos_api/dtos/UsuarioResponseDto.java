package com.sgonzalez.pedido_equipos_api.dtos;

public class UsuarioResponseDto {

	private Long id;
	private String nombre;
	private String email;
	private String rol;
	private Boolean activo;

	public UsuarioResponseDto() {
	}

	public UsuarioResponseDto(Long id, String nombre, String email, String rol, Boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
		this.activo = activo;
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
