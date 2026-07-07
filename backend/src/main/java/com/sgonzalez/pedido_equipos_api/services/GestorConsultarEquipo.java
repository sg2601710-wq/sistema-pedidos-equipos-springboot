package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GestorConsultarEquipo {

	private final IEquipoRepository equipoRepository;

	public GestorConsultarEquipo(IEquipoRepository equipoRepository) {
		this.equipoRepository = equipoRepository;
	}

	public List<EquipoResponseDto> consultarEquipos() {
		return equipoRepository.findAll()
				.stream()
				.map(this::toResponseDto)
				.toList();
	}

	public EquipoResponseDto consultarEquipo(Long id) {
		Equipo equipo = equipoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
		return toResponseDto(equipo);
	}

	EquipoResponseDto toResponseDto(Equipo equipo) {
		return new EquipoResponseDto(
				equipo.getId(),
				equipo.getCodigoInventario(),
				equipo.getNombre(),
				equipo.getCategoria(),
				equipo.getEstado().getNombre(),
				equipo.getUbicacion(),
				equipo.getRequiereAutorizacion()
		);
	}
}
