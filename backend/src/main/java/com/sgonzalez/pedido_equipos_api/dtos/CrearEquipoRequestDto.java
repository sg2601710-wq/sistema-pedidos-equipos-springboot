package com.sgonzalez.pedido_equipos_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearEquipoRequestDto {

	@NotBlank(message = "El codigo de inventario es obligatorio")
	@Size(max = 255, message = "El codigo de inventario no puede superar los 255 caracteres")
	private String codigoInventario;

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
	private String nombre;

	@NotBlank(message = "La categoria es obligatoria")
	@Size(max = 255, message = "La categoria no puede superar los 255 caracteres")
	private String categoria;

	@NotBlank(message = "El estado es obligatorio")
	private String estado;

	@NotBlank(message = "La ubicacion es obligatoria")
	@Size(max = 255, message = "La ubicacion no puede superar los 255 caracteres")
	private String ubicacion;

	@NotNull(message = "Requiere autorizacion es obligatorio")
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
