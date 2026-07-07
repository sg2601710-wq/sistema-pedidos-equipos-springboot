package com.sgonzalez.pedido_equipos_api.dtos;

public class CrearEquipoRequestDto {

	private String codigoInventario;
	private String nombre;
	private String categoria;
	private String estado;
	private String ubicacion;
	private Boolean requiereAutorizacion = true;

	public CrearEquipoRequestDto() {
	}

	public CrearEquipoRequestDto(
			String codigoInventario,
			String nombre,
			String categoria,
			String estado,
			String ubicacion,
			Boolean requiereAutorizacion
	) {
		this.codigoInventario = codigoInventario;
		this.nombre = nombre;
		this.categoria = categoria;
		this.estado = estado;
		this.ubicacion = ubicacion;
		this.requiereAutorizacion = requiereAutorizacion;
	}

	public String getCodigoInventario() {
		return codigoInventario;
	}

	public void setCodigoInventario(String codigoInventario) {
		this.codigoInventario = codigoInventario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Boolean getRequiereAutorizacion() {
		return requiereAutorizacion;
	}

	public void setRequiereAutorizacion(Boolean requiereAutorizacion) {
		this.requiereAutorizacion = requiereAutorizacion;
	}
}
