package com.sgonzalez.pedido_equipos_api.dtos;

public class IniciarSesionResponseDto {

	private UsuarioResponseDto usuario;
	private String token;

	public IniciarSesionResponseDto() {
	}

	public IniciarSesionResponseDto(UsuarioResponseDto usuario, String token) {
		this.usuario = usuario;
		this.token = token;
	}

	public UsuarioResponseDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioResponseDto usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
