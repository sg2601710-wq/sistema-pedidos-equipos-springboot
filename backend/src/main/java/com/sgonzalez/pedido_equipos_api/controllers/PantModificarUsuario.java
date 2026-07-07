package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarUsuarioRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorModificarUsuario;
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
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class PantModificarUsuario {

	private final GestorModificarUsuario gestorModificarUsuario;

	public PantModificarUsuario(GestorModificarUsuario gestorModificarUsuario) {
		this.gestorModificarUsuario = gestorModificarUsuario;
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> modificarUsuario(
			@PathVariable Long id,
			@Valid @RequestBody ActualizarUsuarioRequestDto request
	) {
		UsuarioResponseDto usuario = gestorModificarUsuario.modificarUsuario(id, request);
		return ResponseEntity.ok(ApiResponse.success("Usuario actualizado correctamente", usuario));
	}

}
