package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.PaginationMetaDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudFiltroDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarSolicitud;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "*")
public class PantConsultarSolicitud {

	private final GestorConsultarSolicitud gestorConsultarSolicitud;

	public PantConsultarSolicitud(GestorConsultarSolicitud gestorConsultarSolicitud) {
		this.gestorConsultarSolicitud = gestorConsultarSolicitud;
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<SolicitudResponseDto>>> consultarSolicitudes(
			@ModelAttribute SolicitudFiltroDto filtros
	) {
		Page<SolicitudResponseDto> solicitudes = gestorConsultarSolicitud.consultarSolicitudes(filtros);
		return ResponseEntity.ok(ApiResponse.success(
				"Solicitudes consultadas correctamente",
				solicitudes.getContent(),
				PaginationMetaDto.from(solicitudes, filtros.getSortBy(), filtros.getSortDirection())
		));
	}

}
