package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoFiltroDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.PaginationMetaDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarEquipo;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
public class PantConsultarEquipo {

	private final GestorConsultarEquipo gestorConsultarEquipo;

	public PantConsultarEquipo(GestorConsultarEquipo gestorConsultarEquipo) {
		this.gestorConsultarEquipo = gestorConsultarEquipo;
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<EquipoResponseDto>>> consultarEquipos(@ModelAttribute EquipoFiltroDto filtros) {
		Page<EquipoResponseDto> equipos = gestorConsultarEquipo.consultarEquipos(filtros);
		return ResponseEntity.ok(ApiResponse.success(
				"Equipos consultados correctamente",
				equipos.getContent(),
				PaginationMetaDto.from(equipos, filtros.getSortBy(), filtros.getSortDirection())
		));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<EquipoResponseDto>> consultarEquipo(@PathVariable Long id) {
		EquipoResponseDto equipo = gestorConsultarEquipo.consultarEquipo(id);
		return ResponseEntity.ok(ApiResponse.success("Equipo consultado correctamente", equipo));
	}

}
