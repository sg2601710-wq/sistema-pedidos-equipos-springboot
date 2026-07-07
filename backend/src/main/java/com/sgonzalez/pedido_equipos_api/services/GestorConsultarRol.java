package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.RolResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Rol;
import com.sgonzalez.pedido_equipos_api.repositories.IRolRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GestorConsultarRol {

	private final IRolRepository rolRepository;

	public GestorConsultarRol(IRolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	public List<RolResponseDto> consultarRoles() {
		return rolRepository.findAll().stream().map(this::toResponseDto).toList();
	}

	public RolResponseDto consultarRol(String nombre) {
		Rol rol = rolRepository.findById(normalizar(nombre))
				.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
		return toResponseDto(rol);
	}

	RolResponseDto toResponseDto(Rol rol) {
		return new RolResponseDto(rol.getNombre(), rol.getDescripcion());
	}

	private String normalizar(String value) {
		return value.trim().toUpperCase();
	}
}
