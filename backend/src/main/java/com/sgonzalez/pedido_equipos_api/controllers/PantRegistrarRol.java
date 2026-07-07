package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.CrearRolRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.RolResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorRegistrarRol;
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
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class PantRegistrarRol {

	private final GestorRegistrarRol gestorRegistrarRol;

	public PantRegistrarRol(GestorRegistrarRol gestorRegistrarRol) {
		this.gestorRegistrarRol = gestorRegistrarRol;
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<RolResponseDto>> registrarRol(@Valid @RequestBody CrearRolRequestDto request) {
		RolResponseDto rol = gestorRegistrarRol.registrarRol(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Rol creado correctamente", rol));
	}

}
