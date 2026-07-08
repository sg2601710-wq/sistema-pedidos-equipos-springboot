package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.HealthCheckResponseDto;
import com.sgonzalez.pedido_equipos_api.services.HealthCheckService;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
@CrossOrigin(origins = "*")
@Tag(name = "Health Check", description = "Estado de la API y dependencias")
public class HealthCheckController {

	private final HealthCheckService healthCheckService;

	public HealthCheckController(HealthCheckService healthCheckService) {
		this.healthCheckService = healthCheckService;
	}

	@GetMapping
	@Operation(summary = "Consulta estado de la API", description = "Devuelve estado general, runtime y chequeos basicos del backend.", security = {})
	public ResponseEntity<ApiResponseDto<HealthCheckResponseDto>> getHealthCheck() {
		return ResponseEntity.ok(ApiResponse.success("Health-check obtenido correctamente", healthCheckService.getStatus()));
	}
}
