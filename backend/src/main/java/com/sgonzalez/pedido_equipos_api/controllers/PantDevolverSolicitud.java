package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorDevolverSolicitud;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
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
public class PantDevolverSolicitud {

	private final GestorDevolverSolicitud gestorDevolverSolicitud;

	public PantDevolverSolicitud(GestorDevolverSolicitud gestorDevolverSolicitud) {
		this.gestorDevolverSolicitud = gestorDevolverSolicitud;
	}

	@PatchMapping("/{numSolicitud}/devolver")
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> marcarDevuelta(
			@PathVariable Long numSolicitud,
			Authentication authentication
	) {
		SolicitudResponseDto solicitud = gestorDevolverSolicitud.marcarDevuelta(numSolicitud, authentication.getName());
		return ResponseEntity.ok(ApiResponse.success("Solicitud marcada como devuelta correctamente", solicitud));
	}


}
