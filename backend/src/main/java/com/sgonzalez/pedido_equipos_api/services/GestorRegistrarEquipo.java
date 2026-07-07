package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.CrearEquipoRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorRegistrarEquipo {

	private final IEquipoRepository equipoRepository;
	private final IEstadoRepository estadoRepository;
	private final GestorConsultarEquipo gestorConsultarEquipo;

	public GestorRegistrarEquipo(
			IEquipoRepository equipoRepository,
			IEstadoRepository estadoRepository,
			GestorConsultarEquipo gestorConsultarEquipo
	) {
		this.equipoRepository = equipoRepository;
		this.estadoRepository = estadoRepository;
		this.gestorConsultarEquipo = gestorConsultarEquipo;
	}

	@Transactional
	public EquipoResponseDto registrarEquipo(CrearEquipoRequestDto request) {
		validarRequest(request);
		String codigo = request.getCodigoInventario().trim();

		if (equipoRepository.existsByCodigoInventario(codigo)) {
			throw new IllegalArgumentException("Ya existe un equipo con ese codigo de inventario");
		}

		Equipo equipo = new Equipo(
				codigo,
				request.getNombre().trim(),
				request.getCategoria().trim(),
				buscarEstadoEquipo(request.getEstado()),
				request.getUbicacion().trim(),
				request.getRequiereAutorizacion()
		);

		return gestorConsultarEquipo.toResponseDto(equipoRepository.save(equipo));
	}

	private Estado buscarEstadoEquipo(String nombre) {
		return estadoRepository.findByNombreAndAmbito(nombre.trim().toUpperCase(), "EQUIPO")
				.orElseThrow(() -> new IllegalArgumentException("Estado de equipo no encontrado"));
	}

	private void validarRequest(CrearEquipoRequestDto request) {
		if (request == null || esBlanco(request.getCodigoInventario()) || esBlanco(request.getNombre())
				|| esBlanco(request.getCategoria()) || esBlanco(request.getEstado()) || esBlanco(request.getUbicacion())) {
			throw new IllegalArgumentException("Codigo, nombre, categoria, estado y ubicacion son obligatorios");
		}
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
	}
}
