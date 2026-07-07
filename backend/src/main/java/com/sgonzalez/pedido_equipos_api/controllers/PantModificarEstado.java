package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarEstadoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorModificarEstado;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
@CrossOrigin(origins = "*")
public class PantModificarEstado {

	private final GestorModificarEstado gestorModificarEstado;

	public PantModificarEstado(GestorModificarEstado gestorModificarEstado) {
		this.gestorModificarEstado = gestorModificarEstado;
	}

	@PutMapping("/{nombre}")
	public ResponseEntity<ApiResponseDto<EstadoResponseDto>> modificarEstado(
			@PathVariable String nombre,
			@Valid @RequestBody ActualizarEstadoRequestDto request
	) {
		EstadoResponseDto estado = gestorModificarEstado.modificarEstado(nombre, request);
		return ResponseEntity.ok(ApiResponse.success("Estado actualizado correctamente", estado));
	}

}
