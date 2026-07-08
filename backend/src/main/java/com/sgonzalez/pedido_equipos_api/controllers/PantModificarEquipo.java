package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarEquipoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorModificarEquipo;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
@Tag(name = "Equipos", description = "Administracion del inventario de equipos")
public class PantModificarEquipo {

	private final GestorModificarEquipo gestorModificarEquipo;

	public PantModificarEquipo(GestorModificarEquipo gestorModificarEquipo) {
		this.gestorModificarEquipo = gestorModificarEquipo;
	}

	@PutMapping("/{id}")
	@Operation(summary = "Modifica un equipo", description = "Actualiza los datos de un equipo existente. Requiere rol ADMIN.")
	public ResponseEntity<ApiResponseDto<EquipoResponseDto>> modificarEquipo(
			@PathVariable Long id,
			@Valid @RequestBody ActualizarEquipoRequestDto request
	) {
		EquipoResponseDto equipo = gestorModificarEquipo.modificarEquipo(id, request);
		return ResponseEntity.ok(ApiResponse.success("Equipo actualizado correctamente", equipo));
	}

}
