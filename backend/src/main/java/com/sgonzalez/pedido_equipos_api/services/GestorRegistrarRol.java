package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.CrearRolRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.RolResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Rol;
import com.sgonzalez.pedido_equipos_api.repositories.IRolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorRegistrarRol {

	private final IRolRepository rolRepository;
	private final GestorConsultarRol gestorConsultarRol;

	public GestorRegistrarRol(IRolRepository rolRepository, GestorConsultarRol gestorConsultarRol) {
		this.rolRepository = rolRepository;
		this.gestorConsultarRol = gestorConsultarRol;
	}

	@Transactional
	public RolResponseDto registrarRol(CrearRolRequestDto request) {
		if (request == null || esBlanco(request.getNombre())) {
			throw new IllegalArgumentException("El nombre del rol es obligatorio");
		}

		String nombre = normalizar(request.getNombre());
		if (rolRepository.existsById(nombre)) {
			throw new IllegalArgumentException("Ya existe un rol con ese nombre");
		}

		return gestorConsultarRol.toResponseDto(rolRepository.save(new Rol(nombre, request.getDescripcion())));
	}

	private String normalizar(String value) {
		return value.trim().toUpperCase();
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
	}
}
