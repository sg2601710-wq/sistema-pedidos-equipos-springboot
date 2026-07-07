package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CambiarEstadoSolicitudRequestDto {

	@NotBlank(message = "El estado es obligatorio")
	private String estado;

	@NotNull(message = "El responsable es obligatorio")
	@Positive(message = "El responsable debe ser valido")
	private Long responsableId;

	public CambiarEstadoSolicitudRequestDto() {
	}

	public CambiarEstadoSolicitudRequestDto(String estado, Long responsableId) {
		this.estado = estado;
		this.responsableId = responsableId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Long responsableId) {
		this.responsableId = responsableId;
	}
}
