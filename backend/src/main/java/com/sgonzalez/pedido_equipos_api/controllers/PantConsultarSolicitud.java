package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.PaginationMetaDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudFiltroDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarSolicitud;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "*")
@Tag(name = "Solicitudes", description = "Consulta y seguimiento de solicitudes de equipos")
public class PantConsultarSolicitud {

	private final GestorConsultarSolicitud gestorConsultarSolicitud;

	public PantConsultarSolicitud(GestorConsultarSolicitud gestorConsultarSolicitud) {
		this.gestorConsultarSolicitud = gestorConsultarSolicitud;
	}

	@GetMapping
	@Operation(
			summary = "Lista solicitudes",
			description = "Devuelve solicitudes paginadas y filtradas. Admin y encargado ven todas; usuario normal solo ve las propias."
	)
	public ResponseEntity<ApiResponseDto<List<SolicitudResponseDto>>> consultarSolicitudes(
			@ModelAttribute SolicitudFiltroDto filtros,
			Authentication authentication
	) {
		Page<SolicitudResponseDto> solicitudes = gestorConsultarSolicitud.consultarSolicitudes(filtros, authentication.getName());
		String order = filtros.getOrder() == null ? filtros.getSortDirection() : filtros.getOrder();
		return ResponseEntity.ok(ApiResponse.success(
				"Solicitudes consultadas correctamente",
				solicitudes.getContent(),
				PaginationMetaDto.from(solicitudes, filtros.getSortBy(), order)
		));
	}

}
