package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorEliminarEstado;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
@CrossOrigin(origins = "*")
public class PantEliminarEstado {

	private final GestorEliminarEstado gestorEliminarEstado;

	public PantEliminarEstado(GestorEliminarEstado gestorEliminarEstado) {
		this.gestorEliminarEstado = gestorEliminarEstado;
	}

	@DeleteMapping("/{nombre}")
	public ResponseEntity<ApiResponseDto<Object>> eliminarEstado(@PathVariable String nombre) {
		gestorEliminarEstado.eliminarEstado(nombre);
		return ResponseEntity.ok(ApiResponse.success("Estado eliminado correctamente", null));
	}


}
