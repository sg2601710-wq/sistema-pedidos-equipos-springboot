package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorCancelarSolicitud {

	private final IEquipoRepository equipoRepository;
	private final SolicitudReglasService solicitudReglasService;
	private final SolicitudDtoMapper solicitudDtoMapper;

	public GestorCancelarSolicitud(
			IEquipoRepository equipoRepository,
			SolicitudReglasService solicitudReglasService,
			SolicitudDtoMapper solicitudDtoMapper
	) {
		this.equipoRepository = equipoRepository;
		this.solicitudReglasService = solicitudReglasService;
		this.solicitudDtoMapper = solicitudDtoMapper;
	}

	@Transactional
	public SolicitudResponseDto cancelarSolicitud(Long numSolicitud, String emailResponsable) {
		Solicitud solicitud = solicitudReglasService.buscarSolicitud(numSolicitud);
		Usuario responsable = solicitudReglasService.buscarUsuarioActivo(emailResponsable);
		solicitudReglasService.validarPropietarioOPrivilegiado(solicitud, responsable);

		Estado estadoActual = solicitudReglasService.estadoActual(solicitud);
		if (!"PENDIENTE".equals(estadoActual.getNombre()) && !"APROBADA".equals(estadoActual.getNombre())) {
			throw new IllegalStateException("Solo se pueden cancelar solicitudes pendientes o aprobadas");
		}

		solicitudReglasService.cambiarEstadoSolicitud(solicitud, responsable, "CANCELADA");

		if ("APROBADA".equals(estadoActual.getNombre())) {
			solicitud.getEquipo().setEstado(solicitudReglasService.estadoEquipo("DISPONIBLE"));
			equipoRepository.save(solicitud.getEquipo());
		}

		return solicitudDtoMapper.toResponseDto(solicitud);
	}
}
