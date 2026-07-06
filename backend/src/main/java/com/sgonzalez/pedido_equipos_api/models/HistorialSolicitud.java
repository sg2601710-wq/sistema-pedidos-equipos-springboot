package com.sgonzalez.pedido_equipos_api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_solicitudes")
public class HistorialSolicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "solicitudId", nullable = false)
	private Solicitud solicitud;

	@Column(name = "fechaHoraInicio", nullable = false)
	private LocalDateTime fechaHoraInicio;

	@Column(name = "fechaHoraFin")
	private LocalDateTime fechaHoraFin;

	@ManyToOne(optional = false)
	@JoinColumn(name = "responsableId", nullable = false)
	private Usuario responsable;

	@ManyToOne
	@JoinColumn(name = "estadoAnterior")
	private Estado estadoAnterior;

	@ManyToOne(optional = false)
	@JoinColumn(name = "estado", nullable = false)
	private Estado estado;

	public HistorialSolicitud() {
	}

	public HistorialSolicitud(
			Solicitud solicitud,
			LocalDateTime fechaHoraInicio,
			LocalDateTime fechaHoraFin,
			Usuario responsable,
			Estado estadoAnterior,
			Estado estado
	) {
		this.solicitud = solicitud;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.responsable = responsable;
		this.estadoAnterior = estadoAnterior;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
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

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Estado getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(Estado estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
