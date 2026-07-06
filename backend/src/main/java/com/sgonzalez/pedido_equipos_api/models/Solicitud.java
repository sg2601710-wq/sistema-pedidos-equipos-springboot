package com.sgonzalez.pedido_equipos_api.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "numSolicitud")
	private Long numSolicitud;

	@ManyToOne(optional = false)
	@JoinColumn(name = "equipoId", nullable = false)
	private Equipo equipo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "solicitanteId", nullable = false)
	private Usuario solicitante;

	@Column(name = "fechaRetiro", nullable = false)
	private LocalDate fechaRetiro;

	@Column(name = "fechaDevolucion", nullable = false)
	private LocalDate fechaDevolucion;

	@Column(name = "motivo", nullable = false, length = 1000)
	private String motivo;

	@OneToMany(mappedBy = "solicitud")
	private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();

	public Solicitud() {
	}

	public Solicitud(
			Equipo equipo,
			Usuario solicitante,
			LocalDate fechaRetiro,
			LocalDate fechaDevolucion,
			String motivo
	) {
		this.equipo = equipo;
		this.solicitante = solicitante;
		this.fechaRetiro = fechaRetiro;
		this.fechaDevolucion = fechaDevolucion;
		this.motivo = motivo;
	}

	public Long getNumSolicitud() {
		return numSolicitud;
	}

	public void setNumSolicitud(Long numSolicitud) {
		this.numSolicitud = numSolicitud;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
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

	public List<HistorialSolicitud> getHistorialSolicitud() {
		return historialSolicitud;
	}

	public void setHistorialSolicitud(List<HistorialSolicitud> historialSolicitud) {
		this.historialSolicitud = historialSolicitud;
	}
}
