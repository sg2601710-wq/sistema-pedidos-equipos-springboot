package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorEliminarRol;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class PantEliminarRol {

	private final GestorEliminarRol gestorEliminarRol;

	public PantEliminarRol(GestorEliminarRol gestorEliminarRol) {
		this.gestorEliminarRol = gestorEliminarRol;
	}

	@DeleteMapping("/{nombre}")
	public ResponseEntity<ApiResponseDto<Object>> eliminarRol(@PathVariable String nombre) {
		gestorEliminarRol.eliminarRol(nombre);
		return ResponseEntity.ok(ApiResponse.success("Rol eliminado correctamente", null));
	}


}
