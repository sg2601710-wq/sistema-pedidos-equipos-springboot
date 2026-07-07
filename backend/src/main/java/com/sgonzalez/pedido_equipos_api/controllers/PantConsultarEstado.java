package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarEstado;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
@CrossOrigin(origins = "*")
public class PantConsultarEstado {

	private final GestorConsultarEstado gestorConsultarEstado;

	public PantConsultarEstado(GestorConsultarEstado gestorConsultarEstado) {
		this.gestorConsultarEstado = gestorConsultarEstado;
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<EstadoResponseDto>>> consultarEstados() {
		return ResponseEntity.ok(ApiResponse.success("Estados consultados correctamente", gestorConsultarEstado.consultarEstados()));
	}

	@GetMapping("/{nombre}")
	public ResponseEntity<ApiResponseDto<EstadoResponseDto>> consultarEstado(@PathVariable String nombre) {
		return ResponseEntity.ok(ApiResponse.success("Estado consultado correctamente", gestorConsultarEstado.consultarEstado(nombre)));
	}

}
