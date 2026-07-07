package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.HistorialSolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.models.HistorialSolicitud;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IHistorialSolicitudRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SolicitudDtoMapper {

	private final IHistorialSolicitudRepository historialSolicitudRepository;

	public SolicitudDtoMapper(IHistorialSolicitudRepository historialSolicitudRepository) {
		this.historialSolicitudRepository = historialSolicitudRepository;
	}

	public SolicitudResponseDto toResponseDto(Solicitud solicitud) {
		List<HistorialSolicitudResponseDto> historial = historialSolicitudRepository
				.findBySolicitudNumSolicitudOrderByFechaHoraInicioAsc(solicitud.getNumSolicitud())
				.stream()
				.map(this::toHistorialResponseDto)
				.toList();

		return new SolicitudResponseDto(
				solicitud.getNumSolicitud(),
				toEquipoResponseDto(solicitud.getEquipo()),
				toUsuarioResponseDto(solicitud.getSolicitante()),
				solicitud.getFechaRetiro(),
				solicitud.getFechaDevolucion(),
				solicitud.getMotivo(),
				historial
		);
	}

	private HistorialSolicitudResponseDto toHistorialResponseDto(HistorialSolicitud historial) {
		return new HistorialSolicitudResponseDto(
				historial.getId(),
				historial.getSolicitud().getNumSolicitud(),
				historial.getFechaHoraInicio(),
				historial.getFechaHoraFin(),
				historial.getResponsable().getId(),
				historial.getResponsable().getNombre(),
				historial.getEstadoAnterior() == null ? null : historial.getEstadoAnterior().getNombre(),
				historial.getEstado().getNombre()
		);
	}

	private EquipoResponseDto toEquipoResponseDto(Equipo equipo) {
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

	private UsuarioResponseDto toUsuarioResponseDto(Usuario usuario) {
		return new UsuarioResponseDto(
				usuario.getId(),
				usuario.getNombre(),
				usuario.getEmail(),
				usuario.getRol().getNombre(),
				usuario.getActivo()
		);
	}
}
