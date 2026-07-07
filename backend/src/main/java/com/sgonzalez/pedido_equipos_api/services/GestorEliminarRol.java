package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.models.Rol;
import com.sgonzalez.pedido_equipos_api.repositories.IRolRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorEliminarRol {

	private final IRolRepository rolRepository;

	public GestorEliminarRol(IRolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	@Transactional
	public void eliminarRol(String nombre) {
		Rol rol = rolRepository.findById(nombre.trim().toUpperCase())
				.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
		try {
			rolRepository.delete(rol);
			rolRepository.flush();
		} catch (DataIntegrityViolationException exception) {
			throw new IllegalStateException("No se puede eliminar el rol porque tiene usuarios asociados");
		}
	}
}
