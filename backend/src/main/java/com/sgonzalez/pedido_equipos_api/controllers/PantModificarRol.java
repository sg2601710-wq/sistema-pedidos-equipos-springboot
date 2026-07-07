package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarRolRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.RolResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorModificarRol;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class PantModificarRol {

	private final GestorModificarRol gestorModificarRol;

	public PantModificarRol(GestorModificarRol gestorModificarRol) {
		this.gestorModificarRol = gestorModificarRol;
	}

	@PutMapping("/{nombre}")
	public ResponseEntity<ApiResponseDto<RolResponseDto>> modificarRol(
			@PathVariable String nombre,
			@Valid @RequestBody ActualizarRolRequestDto request
	) {
		RolResponseDto rol = gestorModificarRol.modificarRol(nombre, request);
		return ResponseEntity.ok(ApiResponse.success("Rol actualizado correctamente", rol));
	}

}
