package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CambiarContrasenaUsuarioRequestDto {

	@NotBlank(message = "La contrasena actual es obligatoria")
	private String contrasenaActual;

	@NotBlank(message = "La contrasena nueva es obligatoria")
	@Size(min = 6, max = 72, message = "La contrasena nueva debe tener entre 6 y 72 caracteres")
	private String contrasenaNueva;

	public CambiarContrasenaUsuarioRequestDto() {
	}

	public CambiarContrasenaUsuarioRequestDto(String contrasenaActual, String contrasenaNueva) {
		this.contrasenaActual = contrasenaActual;
		this.contrasenaNueva = contrasenaNueva;
	}

	public String getContrasenaActual() {
		return contrasenaActual;
	}

	public void setContrasenaActual(String contrasenaActual) {
		this.contrasenaActual = contrasenaActual;
	}

	public String getContrasenaNueva() {
		return contrasenaNueva;
	}

	public void setContrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}
}
