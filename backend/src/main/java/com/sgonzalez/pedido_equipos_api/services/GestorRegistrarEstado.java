package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.CrearEstadoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.EstadoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorRegistrarEstado {

	private final IEstadoRepository estadoRepository;
	private final GestorConsultarEstado gestorConsultarEstado;

	public GestorRegistrarEstado(IEstadoRepository estadoRepository, GestorConsultarEstado gestorConsultarEstado) {
		this.estadoRepository = estadoRepository;
		this.gestorConsultarEstado = gestorConsultarEstado;
	}

	@Transactional
	public EstadoResponseDto registrarEstado(CrearEstadoRequestDto request) {
		validarRequest(request);
		String nombre = normalizar(request.getNombre());

		if (estadoRepository.existsById(nombre)) {
			throw new IllegalArgumentException("Ya existe un estado con ese nombre");
		}

		Estado estado = new Estado(nombre, request.getDescripcion(), normalizar(request.getAmbito()), request.getEsFinal());
		return gestorConsultarEstado.toResponseDto(estadoRepository.save(estado));
	}

	private void validarRequest(CrearEstadoRequestDto request) {
		if (request == null || esBlanco(request.getNombre()) || esBlanco(request.getAmbito()) || request.getEsFinal() == null) {
			throw new IllegalArgumentException("Nombre, ambito y esFinal son obligatorios");
		}
	}

	private String normalizar(String value) {
		return value.trim().toUpperCase();
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
	}
}
