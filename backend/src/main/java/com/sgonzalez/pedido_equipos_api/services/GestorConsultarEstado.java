package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GestorConsultarEstado {

	private final IEstadoRepository estadoRepository;

	public GestorConsultarEstado(IEstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}

	public List<EstadoResponseDto> consultarEstados() {
		return estadoRepository.findAll().stream().map(this::toResponseDto).toList();
	}

	public EstadoResponseDto consultarEstado(String nombre) {
		Estado estado = estadoRepository.findById(normalizar(nombre))
				.orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
		return toResponseDto(estado);
	}

	EstadoResponseDto toResponseDto(Estado estado) {
		return new EstadoResponseDto(estado.getNombre(), estado.getDescripcion(), estado.getAmbito(), estado.getEsFinal());
	}

	private String normalizar(String value) {
		return value.trim().toUpperCase();
	}
}
