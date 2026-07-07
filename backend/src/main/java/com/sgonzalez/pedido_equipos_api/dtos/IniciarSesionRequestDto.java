package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class IniciarSesionRequestDto {

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email debe tener un formato valido")
	private String email;

	@NotBlank(message = "La contrasena es obligatoria")
	private String contrasena;

	public IniciarSesionRequestDto() {
	}

	public IniciarSesionRequestDto(String email, String contrasena) {
		this.email = email;
		this.contrasena = contrasena;
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
}
