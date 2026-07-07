package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarUsuario;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class PantConsultarUsuario {

	private final GestorConsultarUsuario gestorConsultarUsuario;

	public PantConsultarUsuario(GestorConsultarUsuario gestorConsultarUsuario) {
		this.gestorConsultarUsuario = gestorConsultarUsuario;
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<UsuarioResponseDto>>> consultarUsuarios() {
		return ResponseEntity.ok(ApiResponse.success("Usuarios consultados correctamente", gestorConsultarUsuario.consultarUsuarios()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> consultarUsuario(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success("Usuario consultado correctamente", gestorConsultarUsuario.consultarUsuario(id)));
	}

	@PostMapping("/iniciar")
	public ResponseEntity<ApiResponseDto<List<UsuarioResponseDto>>> opcionRegUsuario() {
		return ResponseEntity.ok(ApiResponse.success("Usuarios obtenidos correctamente", gestorConsultarUsuario.consultarUsuarios()));
	}

}
