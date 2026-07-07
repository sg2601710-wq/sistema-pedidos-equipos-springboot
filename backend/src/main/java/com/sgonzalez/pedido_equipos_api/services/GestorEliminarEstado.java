package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorEliminarEstado {

	private final IEstadoRepository estadoRepository;

	public GestorEliminarEstado(IEstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}

	@Transactional
	public void eliminarEstado(String nombre) {
		Estado estado = estadoRepository.findById(nombre.trim().toUpperCase())
				.orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
		try {
			estadoRepository.delete(estado);
			estadoRepository.flush();
		} catch (DataIntegrityViolationException exception) {
			throw new IllegalStateException("No se puede eliminar el estado porque tiene datos asociados");
		}
	}
}
