package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorCancelarSolicitud;
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
public class PantCancelarSolicitud {

	private final GestorCancelarSolicitud gestorCancelarSolicitud;

	public PantCancelarSolicitud(GestorCancelarSolicitud gestorCancelarSolicitud) {
		this.gestorCancelarSolicitud = gestorCancelarSolicitud;
	}

	@PatchMapping("/{numSolicitud}/cancelar")
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> cancelarSolicitud(
			@PathVariable Long numSolicitud,
			Authentication authentication
	) {
		SolicitudResponseDto solicitud = gestorCancelarSolicitud.cancelarSolicitud(numSolicitud, authentication.getName());
		return ResponseEntity.ok(ApiResponse.success("Solicitud cancelada correctamente", solicitud));
	}


}
