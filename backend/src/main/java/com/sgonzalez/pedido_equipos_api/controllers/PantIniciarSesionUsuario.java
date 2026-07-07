package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.IniciarSesionRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.IniciarSesionResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorIniciarSesionUsuario;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios/sesion")
@CrossOrigin(origins = "*")
public class PantIniciarSesionUsuario {

	private final GestorIniciarSesionUsuario gestorIniciarSesionUsuario;

	public PantIniciarSesionUsuario(GestorIniciarSesionUsuario gestorIniciarSesionUsuario) {
		this.gestorIniciarSesionUsuario = gestorIniciarSesionUsuario;
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<IniciarSesionResponseDto>> iniciarSesion(
			@Valid @RequestBody IniciarSesionRequestDto request
	) {
		IniciarSesionResponseDto sesion = gestorIniciarSesionUsuario.iniciarSesion(request);
		return ResponseEntity.ok(ApiResponse.success("Inicio de sesion correcto", sesion));
	}


}
