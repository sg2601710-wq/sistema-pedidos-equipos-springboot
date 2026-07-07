package com.sgonzalez.pedido_equipos_api.dtos;

import java.time.LocalDateTime;

public class HistorialSolicitudResponseDto {

	private Long id;
	private Long solicitudId;
	private LocalDateTime fechaHoraInicio;
	private LocalDateTime fechaHoraFin;
	private Long responsableId;
	private String responsableNombre;
	private String estadoAnterior;
	private String estado;

	public HistorialSolicitudResponseDto() {
	}

	public HistorialSolicitudResponseDto(
			Long id,
			Long solicitudId,
			LocalDateTime fechaHoraInicio,
			LocalDateTime fechaHoraFin,
			Long responsableId,
			String responsableNombre,
			String estadoAnterior,
			String estado
	) {
		this.id = id;
		this.solicitudId = solicitudId;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.responsableId = responsableId;
		this.responsableNombre = responsableNombre;
		this.estadoAnterior = estadoAnterior;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(Long solicitudId) {
		this.solicitudId = solicitudId;
	}

	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public LocalDateTime getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public Long getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Long responsableId) {
		this.responsableId = responsableId;
	}

	public String getResponsableNombre() {
		return responsableNombre;
	}

	public void setResponsableNombre(String responsableNombre) {
		this.responsableNombre = responsableNombre;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
