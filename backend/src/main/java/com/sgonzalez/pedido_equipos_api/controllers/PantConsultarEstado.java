package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarEstado;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Estados", description = "Consulta de estados del sistema")
public class PantConsultarEstado {

	private final GestorConsultarEstado gestorConsultarEstado;

	public PantConsultarEstado(GestorConsultarEstado gestorConsultarEstado) {
		this.gestorConsultarEstado = gestorConsultarEstado;
	}

	@GetMapping
	@Operation(summary = "Lista estados", description = "Devuelve los estados configurados para equipos y solicitudes.")
	public ResponseEntity<ApiResponseDto<List<EstadoResponseDto>>> consultarEstados() {
		return ResponseEntity.ok(ApiResponse.success("Estados consultados correctamente", gestorConsultarEstado.consultarEstados()));
	}

	@GetMapping("/{nombre}")
	@Operation(summary = "Obtiene un estado", description = "Devuelve un estado por nombre.")
	public ResponseEntity<ApiResponseDto<EstadoResponseDto>> consultarEstado(@PathVariable String nombre) {
		return ResponseEntity.ok(ApiResponse.success("Estado consultado correctamente", gestorConsultarEstado.consultarEstado(nombre)));
	}

}
