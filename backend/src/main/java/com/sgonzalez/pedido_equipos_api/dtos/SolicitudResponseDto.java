package com.sgonzalez.pedido_equipos_api.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudResponseDto {

	private Long numSolicitud;
	private EquipoResponseDto equipo;
	private UsuarioResponseDto solicitante;
	private LocalDate fechaRetiro;
	private LocalDate fechaDevolucion;
	private String motivo;
	private List<HistorialSolicitudResponseDto> historialSolicitud = new ArrayList<>();

	public SolicitudResponseDto() {
	}

	public SolicitudResponseDto(
			Long numSolicitud,
			EquipoResponseDto equipo,
			UsuarioResponseDto solicitante,
			LocalDate fechaRetiro,
			LocalDate fechaDevolucion,
			String motivo,
			List<HistorialSolicitudResponseDto> historialSolicitud
	) {
		this.numSolicitud = numSolicitud;
		this.equipo = equipo;
		this.solicitante = solicitante;
		this.fechaRetiro = fechaRetiro;
		this.fechaDevolucion = fechaDevolucion;
		this.motivo = motivo;
		this.historialSolicitud = historialSolicitud;
	}

	public Long getNumSolicitud() {
		return numSolicitud;
	}

	public void setNumSolicitud(Long numSolicitud) {
		this.numSolicitud = numSolicitud;
	}

	public EquipoResponseDto getEquipo() {
		return equipo;
	}

	public void setEquipo(EquipoResponseDto equipo) {
		this.equipo = equipo;
	}

	public UsuarioResponseDto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(UsuarioResponseDto solicitante) {
		this.solicitante = solicitante;
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

	public List<HistorialSolicitudResponseDto> getHistorialSolicitud() {
		return historialSolicitud;
	}

	public void setHistorialSolicitud(List<HistorialSolicitudResponseDto> historialSolicitud) {
		this.historialSolicitud = historialSolicitud;
	}
}
