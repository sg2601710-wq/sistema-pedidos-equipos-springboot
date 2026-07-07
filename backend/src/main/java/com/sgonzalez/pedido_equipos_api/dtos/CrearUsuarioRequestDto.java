package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearUsuarioRequestDto {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
	private String nombre;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email debe tener un formato valido")
	@Size(max = 255, message = "El email no puede superar los 255 caracteres")
	private String email;

	@NotBlank(message = "La contrasena es obligatoria")
	@Size(min = 6, max = 72, message = "La contrasena debe tener entre 6 y 72 caracteres")
	private String contrasena;

	@NotBlank(message = "El rol es obligatorio")
	private String rol;

	@NotNull(message = "Activo es obligatorio")
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
