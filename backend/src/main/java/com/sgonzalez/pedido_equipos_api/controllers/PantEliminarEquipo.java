package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorEliminarEquipo;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
@Tag(name = "Equipos", description = "Administracion del inventario de equipos")
public class PantEliminarEquipo {

	private final GestorEliminarEquipo gestorEliminarEquipo;

	public PantEliminarEquipo(GestorEliminarEquipo gestorEliminarEquipo) {
		this.gestorEliminarEquipo = gestorEliminarEquipo;
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Elimina un equipo", description = "Elimina un equipo si no tiene datos asociados. Requiere rol ADMIN.")
	public ResponseEntity<ApiResponseDto<Object>> eliminarEquipo(@PathVariable Long id) {
		gestorEliminarEquipo.eliminarEquipo(id);
		return ResponseEntity.ok(ApiResponse.success("Equipo eliminado correctamente", null));
	}


}
