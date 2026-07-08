package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorDevolverSolicitud;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "*")
@Tag(name = "Solicitudes", description = "Cambio de estado de solicitudes")
public class PantDevolverSolicitud {

	private final GestorDevolverSolicitud gestorDevolverSolicitud;

	public PantDevolverSolicitud(GestorDevolverSolicitud gestorDevolverSolicitud) {
		this.gestorDevolverSolicitud = gestorDevolverSolicitud;
	}

	@PatchMapping("/{numSolicitud}/devolver")
	@Operation(summary = "Marca una solicitud como devuelta", description = "Marca una solicitud aprobada como devuelta y libera el equipo. Requiere rol ADMIN o ENCARGADO.")
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> marcarDevuelta(
			@PathVariable Long numSolicitud,
			Authentication authentication
	) {
		SolicitudResponseDto solicitud = gestorDevolverSolicitud.marcarDevuelta(numSolicitud, authentication.getName());
		return ResponseEntity.ok(ApiResponse.success("Solicitud marcada como devuelta correctamente", solicitud));
	}


}
