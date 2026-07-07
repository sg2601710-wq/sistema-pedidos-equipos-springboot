package com.sgonzalez.pedido_equipos_api.dtos;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class SolicitudFiltroDto {

	private Long usuarioId;
	private Integer page = 0;
	private Integer size = 10;
	private String sortBy = "numSolicitud";
	private String sortDirection = "desc";
	private Long numSolicitud;
	private Long equipoId;
	private String codigoInventario;
	private Long solicitanteId;
	private String estado;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaRetiroDesde;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaRetiroHasta;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaDevolucionDesde;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaDevolucionHasta;
	private String motivo;

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public Long getNumSolicitud() {
		return numSolicitud;
	}

	public void setNumSolicitud(Long numSolicitud) {
		this.numSolicitud = numSolicitud;
	}

	public Long getEquipoId() {
		return equipoId;
	}

	public void setEquipoId(Long equipoId) {
		this.equipoId = equipoId;
	}

	public String getCodigoInventario() {
		return codigoInventario;
	}

	public void setCodigoInventario(String codigoInventario) {
		this.codigoInventario = codigoInventario;
	}

	public Long getSolicitanteId() {
		return solicitanteId;
	}

	public void setSolicitanteId(Long solicitanteId) {
		this.solicitanteId = solicitanteId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFechaRetiroDesde() {
		return fechaRetiroDesde;
	}

	public void setFechaRetiroDesde(LocalDate fechaRetiroDesde) {
		this.fechaRetiroDesde = fechaRetiroDesde;
	}

	public LocalDate getFechaRetiroHasta() {
		return fechaRetiroHasta;
	}

	public void setFechaRetiroHasta(LocalDate fechaRetiroHasta) {
		this.fechaRetiroHasta = fechaRetiroHasta;
	}

	public LocalDate getFechaDevolucionDesde() {
		return fechaDevolucionDesde;
	}

	public void setFechaDevolucionDesde(LocalDate fechaDevolucionDesde) {
		this.fechaDevolucionDesde = fechaDevolucionDesde;
	}

	public LocalDate getFechaDevolucionHasta() {
		return fechaDevolucionHasta;
	}

	public void setFechaDevolucionHasta(LocalDate fechaDevolucionHasta) {
		this.fechaDevolucionHasta = fechaDevolucionHasta;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
