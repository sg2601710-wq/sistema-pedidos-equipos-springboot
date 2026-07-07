package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.CrearUsuarioRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorRegistrarUsuario;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class PantRegistrarUsuario {

	private final GestorRegistrarUsuario gestorRegistrarUsuario;

	public PantRegistrarUsuario(GestorRegistrarUsuario gestorRegistrarUsuario) {
		this.gestorRegistrarUsuario = gestorRegistrarUsuario;
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> registrarUsuario(@Valid @RequestBody CrearUsuarioRequestDto request) {
		UsuarioResponseDto usuario = gestorRegistrarUsuario.registrarUsuario(request);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Usuario registrado correctamente", usuario));
	}

}
