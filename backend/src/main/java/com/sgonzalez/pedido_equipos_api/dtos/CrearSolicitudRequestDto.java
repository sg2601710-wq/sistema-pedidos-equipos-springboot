package com.sgonzalez.pedido_equipos_api.dtos;

import java.time.LocalDate;

public class CrearSolicitudRequestDto {

	private Long equipoId;
	private Long solicitanteId;
	private LocalDate fechaRetiro;
	private LocalDate fechaDevolucion;
	private String motivo;

	public CrearSolicitudRequestDto() {
	}

	public CrearSolicitudRequestDto(
			Long equipoId,
			Long solicitanteId,
			LocalDate fechaRetiro,
			LocalDate fechaDevolucion,
			String motivo
	) {
		this.equipoId = equipoId;
		this.solicitanteId = solicitanteId;
		this.fechaRetiro = fechaRetiro;
		this.fechaDevolucion = fechaDevolucion;
		this.motivo = motivo;
	}

	public Long getEquipoId() {
		return equipoId;
	}

	public void setEquipoId(Long equipoId) {
		this.equipoId = equipoId;
	}

	public Long getSolicitanteId() {
		return solicitanteId;
	}

	public void setSolicitanteId(Long solicitanteId) {
		this.solicitanteId = solicitanteId;
	}

	public LocalDate getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(LocalDate fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
