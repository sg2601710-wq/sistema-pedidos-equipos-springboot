package com.sgonzalez.pedido_equipos_api.dtos;

public class CrearUsuarioRequestDto {

	private String nombre;
	private String email;
	private String contrasena;
	private String rol;
	private Boolean activo = true;

	public CrearUsuarioRequestDto() {
	}

	public CrearUsuarioRequestDto(String nombre, String email, String contrasena, String rol, Boolean activo) {
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.rol = rol;
		this.activo = activo;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
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
