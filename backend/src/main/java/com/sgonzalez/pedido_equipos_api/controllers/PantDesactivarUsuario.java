package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorDesactivarUsuario;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class PantDesactivarUsuario {

	private final GestorDesactivarUsuario gestorDesactivarUsuario;

	public PantDesactivarUsuario(GestorDesactivarUsuario gestorDesactivarUsuario) {
		this.gestorDesactivarUsuario = gestorDesactivarUsuario;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> desactivarUsuario(@PathVariable Long id) {
		UsuarioResponseDto usuario = gestorDesactivarUsuario.desactivarUsuario(id);
		return ResponseEntity.ok(ApiResponse.success("Usuario desactivado correctamente", usuario));
	}

}
