package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarRolRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.RolResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Rol;
import com.sgonzalez.pedido_equipos_api.repositories.IRolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorModificarRol {

	private final IRolRepository rolRepository;
	private final GestorConsultarRol gestorConsultarRol;

	public GestorModificarRol(IRolRepository rolRepository, GestorConsultarRol gestorConsultarRol) {
		this.rolRepository = rolRepository;
		this.gestorConsultarRol = gestorConsultarRol;
	}

	@Transactional
	public RolResponseDto modificarRol(String nombre, ActualizarRolRequestDto request) {
		if (request == null) {
			throw new IllegalArgumentException("Los datos del rol son obligatorios");
		}

		Rol rol = rolRepository.findById(nombre.trim().toUpperCase())
				.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
		rol.setDescripcion(request.getDescripcion());
		return gestorConsultarRol.toResponseDto(rolRepository.save(rol));
	}
}
