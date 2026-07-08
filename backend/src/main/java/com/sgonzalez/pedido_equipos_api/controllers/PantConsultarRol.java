package com.sgonzalez.pedido_equipos_api.controllers;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.RolResponseDto;
import com.sgonzalez.pedido_equipos_api.services.GestorConsultarRol;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
@Tag(name = "Roles", description = "Consulta de roles del sistema")
public class PantConsultarRol {

	private final GestorConsultarRol gestorConsultarRol;

	public PantConsultarRol(GestorConsultarRol gestorConsultarRol) {
		this.gestorConsultarRol = gestorConsultarRol;
	}

	@GetMapping
	@Operation(summary = "Lista roles", description = "Devuelve los roles disponibles para usuarios.")
	public ResponseEntity<ApiResponseDto<List<RolResponseDto>>> consultarRoles() {
		return ResponseEntity.ok(ApiResponse.success("Roles consultados correctamente", gestorConsultarRol.consultarRoles()));
	}

	@GetMapping("/{nombre}")
	@Operation(summary = "Obtiene un rol", description = "Devuelve un rol por nombre.")
	public ResponseEntity<ApiResponseDto<RolResponseDto>> consultarRol(@PathVariable String nombre) {
		return ResponseEntity.ok(ApiResponse.success("Rol consultado correctamente", gestorConsultarRol.consultarRol(nombre)));
	}

}
