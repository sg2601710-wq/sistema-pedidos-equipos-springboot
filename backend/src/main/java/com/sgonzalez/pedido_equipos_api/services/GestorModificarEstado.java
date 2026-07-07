package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarEstadoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorModificarEstado {

	private final IEstadoRepository estadoRepository;
	private final GestorConsultarEstado gestorConsultarEstado;

	public GestorModificarEstado(IEstadoRepository estadoRepository, GestorConsultarEstado gestorConsultarEstado) {
		this.estadoRepository = estadoRepository;
		this.gestorConsultarEstado = gestorConsultarEstado;
	}

	@Transactional
	public EstadoResponseDto modificarEstado(String nombre, ActualizarEstadoRequestDto request) {
		validarRequest(request);
		Estado estado = estadoRepository.findById(normalizar(nombre))
				.orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
		estado.setDescripcion(request.getDescripcion());
		estado.setAmbito(normalizar(request.getAmbito()));
		estado.setEsFinal(request.getEsFinal());
		return gestorConsultarEstado.toResponseDto(estadoRepository.save(estado));
	}

	private void validarRequest(ActualizarEstadoRequestDto request) {
		if (request == null || esBlanco(request.getAmbito()) || request.getEsFinal() == null) {
			throw new IllegalArgumentException("Ambito y esFinal son obligatorios");
		}
	}

	private String normalizar(String value) {
		return value.trim().toUpperCase();
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
	}
}
