package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ActualizarEstadoRequestDto {

	@Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
	private String descripcion;

	@NotBlank(message = "El ambito es obligatorio")
	@Size(max = 50, message = "El ambito no puede superar los 50 caracteres")
	private String ambito;

	@NotNull(message = "Es final es obligatorio")
	private Boolean esFinal;

	public ActualizarEstadoRequestDto() {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public Boolean getEsFinal() {
		return esFinal;
	}

	public void setEsFinal(Boolean esFinal) {
		this.esFinal = esFinal;
	}
}
