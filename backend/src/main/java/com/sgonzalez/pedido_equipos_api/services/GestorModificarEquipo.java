package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarEquipoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorModificarEquipo {

	private final IEquipoRepository equipoRepository;
	private final IEstadoRepository estadoRepository;
	private final GestorConsultarEquipo gestorConsultarEquipo;

	public GestorModificarEquipo(
			IEquipoRepository equipoRepository,
			IEstadoRepository estadoRepository,
			GestorConsultarEquipo gestorConsultarEquipo
	) {
		this.equipoRepository = equipoRepository;
		this.estadoRepository = estadoRepository;
		this.gestorConsultarEquipo = gestorConsultarEquipo;
	}

	@Transactional
	public EquipoResponseDto modificarEquipo(Long id, ActualizarEquipoRequestDto request) {
		validarRequest(request);
		Equipo equipo = equipoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
		String codigo = request.getCodigoInventario().trim();

		equipoRepository.findByCodigoInventario(codigo)
				.filter(e -> !e.getId().equals(id))
				.ifPresent(e -> {
					throw new IllegalArgumentException("Ya existe un equipo con ese codigo de inventario");
				});

		equipo.setCodigoInventario(codigo);
		equipo.setNombre(request.getNombre().trim());
		equipo.setCategoria(request.getCategoria().trim());
		equipo.setEstado(buscarEstadoEquipo(request.getEstado()));
		equipo.setUbicacion(request.getUbicacion().trim());
		equipo.setRequiereAutorizacion(request.getRequiereAutorizacion());

		return gestorConsultarEquipo.toResponseDto(equipoRepository.save(equipo));
	}

	private Estado buscarEstadoEquipo(String nombre) {
		return estadoRepository.findByNombreAndAmbito(nombre.trim().toUpperCase(), "EQUIPO")
				.orElseThrow(() -> new IllegalArgumentException("Estado de equipo no encontrado"));
	}

	private void validarRequest(ActualizarEquipoRequestDto request) {
		if (request == null || esBlanco(request.getCodigoInventario()) || esBlanco(request.getNombre())
				|| esBlanco(request.getCategoria()) || esBlanco(request.getEstado()) || esBlanco(request.getUbicacion())) {
			throw new IllegalArgumentException("Codigo, nombre, categoria, estado y ubicacion son obligatorios");
		}
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
	}
}
