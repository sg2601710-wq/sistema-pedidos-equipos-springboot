package com.sgonzalez.pedido_equipos_api.dtos;

import java.time.LocalDate;

public class ActualizarSolicitudRequestDto {

	private Long equipoId;
	private LocalDate fechaRetiro;
	private LocalDate fechaDevolucion;
	private String motivo;

	public ActualizarSolicitudRequestDto() {
	}

	public ActualizarSolicitudRequestDto(
			Long equipoId,
			LocalDate fechaRetiro,
			LocalDate fechaDevolucion,
			String motivo
	) {
		this.equipoId = equipoId;
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
