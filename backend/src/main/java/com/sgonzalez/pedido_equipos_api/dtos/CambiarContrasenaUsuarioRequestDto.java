package com.sgonzalez.pedido_equipos_api.dtos;

public class CambiarContrasenaUsuarioRequestDto {

	private String contrasenaActual;
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
