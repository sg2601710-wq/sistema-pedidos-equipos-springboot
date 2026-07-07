package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorDevolverSolicitud {

	private final IEquipoRepository equipoRepository;
	private final SolicitudReglasService solicitudReglasService;
	private final SolicitudDtoMapper solicitudDtoMapper;

	public GestorDevolverSolicitud(
			IEquipoRepository equipoRepository,
			SolicitudReglasService solicitudReglasService,
			SolicitudDtoMapper solicitudDtoMapper
	) {
		this.equipoRepository = equipoRepository;
		this.solicitudReglasService = solicitudReglasService;
		this.solicitudDtoMapper = solicitudDtoMapper;
	}

	@Transactional
	public SolicitudResponseDto marcarDevuelta(Long numSolicitud, String emailResponsable) {
		Solicitud solicitud = solicitudReglasService.buscarSolicitud(numSolicitud);
		Usuario responsable = solicitudReglasService.buscarUsuarioActivo(emailResponsable);
		solicitudReglasService.validarAdminOEncargado(responsable);
		solicitudReglasService.validarEstadoActual(solicitud, "APROBADA");

		solicitudReglasService.cambiarEstadoSolicitud(solicitud, responsable, "DEVUELTA");
		solicitud.getEquipo().setEstado(solicitudReglasService.estadoEquipo("DISPONIBLE"));
		equipoRepository.save(solicitud.getEquipo());

		return solicitudDtoMapper.toResponseDto(solicitud);
	}
}
