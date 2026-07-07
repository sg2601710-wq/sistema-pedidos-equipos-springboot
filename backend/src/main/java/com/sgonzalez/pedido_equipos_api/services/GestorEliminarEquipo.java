package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorEliminarEquipo {

	private final IEquipoRepository equipoRepository;

	public GestorEliminarEquipo(IEquipoRepository equipoRepository) {
		this.equipoRepository = equipoRepository;
	}

	@Transactional
	public void eliminarEquipo(Long id) {
		Equipo equipo = equipoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
		try {
			equipoRepository.delete(equipo);
			equipoRepository.flush();
		} catch (DataIntegrityViolationException exception) {
			throw new IllegalStateException("No se puede eliminar el equipo porque tiene datos asociados");
		}
	}
}
