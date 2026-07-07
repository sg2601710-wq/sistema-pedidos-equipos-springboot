package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarSolicitudRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorEditarSolicitud;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "*")
public class PantEditarSolicitud {

	private final GestorEditarSolicitud gestorEditarSolicitud;

	public PantEditarSolicitud(GestorEditarSolicitud gestorEditarSolicitud) {
		this.gestorEditarSolicitud = gestorEditarSolicitud;
	}

	@PutMapping("/{numSolicitud}")
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> editarSolicitud(
			@PathVariable Long numSolicitud,
			@Valid @RequestBody ActualizarSolicitudRequestDto request,
			Authentication authentication
	) {
		SolicitudResponseDto solicitud = gestorEditarSolicitud.editarSolicitud(numSolicitud, request, authentication.getName());
		return ResponseEntity.ok(ApiResponse.success("Solicitud actualizada correctamente", solicitud));
	}


}
