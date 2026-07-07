package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorAprobarSolicitud {

	private final IEquipoRepository equipoRepository;
	private final SolicitudReglasService solicitudReglasService;
	private final SolicitudDtoMapper solicitudDtoMapper;

	public GestorAprobarSolicitud(
			IEquipoRepository equipoRepository,
			SolicitudReglasService solicitudReglasService,
			SolicitudDtoMapper solicitudDtoMapper
	) {
		this.equipoRepository = equipoRepository;
		this.solicitudReglasService = solicitudReglasService;
		this.solicitudDtoMapper = solicitudDtoMapper;
	}

	@Transactional
	public SolicitudResponseDto aprobarSolicitud(Long numSolicitud, String emailResponsable) {
		Solicitud solicitud = solicitudReglasService.buscarSolicitud(numSolicitud);
		Usuario responsable = solicitudReglasService.buscarUsuarioActivo(emailResponsable);
		solicitudReglasService.validarAdminOEncargado(responsable);
		solicitudReglasService.validarEstadoActual(solicitud, "PENDIENTE");

		if (!"DISPONIBLE".equals(solicitud.getEquipo().getEstado().getNombre())) {
			throw new IllegalStateException("El equipo debe estar disponible para aprobar la solicitud");
		}

		solicitudReglasService.validarSinSuperposicion(
				solicitud.getEquipo(),
				solicitud.getFechaRetiro(),
				solicitud.getFechaDevolucion(),
				solicitud.getNumSolicitud()
		);

		solicitudReglasService.cambiarEstadoSolicitud(solicitud, responsable, "APROBADA");
		solicitud.getEquipo().setEstado(solicitudReglasService.estadoEquipo("PRESTADO"));
		equipoRepository.save(solicitud.getEquipo());

		return solicitudDtoMapper.toResponseDto(solicitud);
	}
}
