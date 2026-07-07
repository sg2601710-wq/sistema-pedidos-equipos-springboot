package com.sgonzalez.pedido_equipos_api.dtos;

public class CambiarEstadoSolicitudRequestDto {

	private String estado;
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
