package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.Size;

public class ActualizarRolRequestDto {

	@Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
	private String descripcion;

	public ActualizarRolRequestDto() {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
