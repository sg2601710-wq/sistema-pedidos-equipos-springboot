package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CrearSolicitudRequestDto {

	@NotNull(message = "El equipo es obligatorio")
	@Positive(message = "El equipo debe ser valido")
	private Long equipoId;

	@NotNull(message = "El solicitante es obligatorio")
	@Positive(message = "El solicitante debe ser valido")
	private Long solicitanteId;

	@NotNull(message = "La fecha de retiro es obligatoria")
	private LocalDate fechaRetiro;

	@NotNull(message = "La fecha de devolucion es obligatoria")
	private LocalDate fechaDevolucion;

	@NotBlank(message = "El motivo es obligatorio")
	@Size(max = 1000, message = "El motivo no puede superar los 1000 caracteres")
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
