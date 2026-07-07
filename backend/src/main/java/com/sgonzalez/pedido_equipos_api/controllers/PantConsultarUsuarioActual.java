package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarUsuarioActual;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios/me")
@CrossOrigin(origins = "*")
public class PantConsultarUsuarioActual {

	private final GestorConsultarUsuarioActual gestorConsultarUsuarioActual;

	public PantConsultarUsuarioActual(GestorConsultarUsuarioActual gestorConsultarUsuarioActual) {
		this.gestorConsultarUsuarioActual = gestorConsultarUsuarioActual;
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> consultarUsuarioActual(Authentication authentication) {
		UsuarioResponseDto usuario = gestorConsultarUsuarioActual.consultarUsuarioActual(authentication.getName());
		return ResponseEntity.ok(ApiResponse.success("Usuario actual consultado correctamente", usuario));
	}

}
