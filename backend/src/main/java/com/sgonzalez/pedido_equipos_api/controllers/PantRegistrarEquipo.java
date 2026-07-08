package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.CrearEquipoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorRegistrarEquipo;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
@Tag(name = "Equipos", description = "Administracion del inventario de equipos")
public class PantRegistrarEquipo {

	private final GestorRegistrarEquipo gestorRegistrarEquipo;

	public PantRegistrarEquipo(GestorRegistrarEquipo gestorRegistrarEquipo) {
		this.gestorRegistrarEquipo = gestorRegistrarEquipo;
	}

	@PostMapping
	@Operation(summary = "Crea un equipo", description = "Registra un nuevo equipo en el inventario. Requiere rol ADMIN.")
	public ResponseEntity<ApiResponseDto<EquipoResponseDto>> registrarEquipo(@Valid @RequestBody CrearEquipoRequestDto request) {
		EquipoResponseDto equipo = gestorRegistrarEquipo.registrarEquipo(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Equipo creado correctamente", equipo));
	}

}
