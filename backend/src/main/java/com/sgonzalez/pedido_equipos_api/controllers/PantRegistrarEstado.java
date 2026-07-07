package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.CrearEstadoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorRegistrarEstado;
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
@RequestMapping("/estados")
@CrossOrigin(origins = "*")
public class PantRegistrarEstado {

	private final GestorRegistrarEstado gestorRegistrarEstado;

	public PantRegistrarEstado(GestorRegistrarEstado gestorRegistrarEstado) {
		this.gestorRegistrarEstado = gestorRegistrarEstado;
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<EstadoResponseDto>> registrarEstado(@Valid @RequestBody CrearEstadoRequestDto request) {
		EstadoResponseDto estado = gestorRegistrarEstado.registrarEstado(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Estado creado correctamente", estado));
	}

}
