package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorAprobarSolicitud;
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
public class PantAprobarSolicitud {

	private final GestorAprobarSolicitud gestorAprobarSolicitud;

	public PantAprobarSolicitud(GestorAprobarSolicitud gestorAprobarSolicitud) {
		this.gestorAprobarSolicitud = gestorAprobarSolicitud;
	}

	@PatchMapping("/{numSolicitud}/aprobar")
	@Operation(summary = "Aprueba una solicitud", description = "Aprueba una solicitud pendiente. Requiere rol ADMIN o ENCARGADO.")
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> aprobarSolicitud(
			@PathVariable Long numSolicitud,
			Authentication authentication
	) {
		SolicitudResponseDto solicitud = gestorAprobarSolicitud.aprobarSolicitud(numSolicitud, authentication.getName());
		return ResponseEntity.ok(ApiResponse.success("Solicitud aprobada correctamente", solicitud));
	}


}
