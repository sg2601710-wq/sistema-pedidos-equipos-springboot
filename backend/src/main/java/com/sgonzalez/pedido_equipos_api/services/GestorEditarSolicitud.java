package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarSolicitudRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import com.sgonzalez.pedido_equipos_api.repositories.ISolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorEditarSolicitud {

	private final ISolicitudRepository solicitudRepository;
	private final IEquipoRepository equipoRepository;
	private final SolicitudReglasService solicitudReglasService;
	private final SolicitudDtoMapper solicitudDtoMapper;

	public GestorEditarSolicitud(
			ISolicitudRepository solicitudRepository,
			IEquipoRepository equipoRepository,
			SolicitudReglasService solicitudReglasService,
			SolicitudDtoMapper solicitudDtoMapper
	) {
		this.solicitudRepository = solicitudRepository;
		this.equipoRepository = equipoRepository;
		this.solicitudReglasService = solicitudReglasService;
		this.solicitudDtoMapper = solicitudDtoMapper;
	}

	@Transactional
	public SolicitudResponseDto editarSolicitud(Long numSolicitud, ActualizarSolicitudRequestDto request, String emailResponsable) {
		solicitudReglasService.validarFechas(request.getFechaRetiro(), request.getFechaDevolucion());

		Solicitud solicitud = solicitudReglasService.buscarSolicitud(numSolicitud);
		Usuario responsable = solicitudReglasService.buscarUsuarioActivo(emailResponsable);
		solicitudReglasService.validarPropietarioOPrivilegiado(solicitud, responsable);
		solicitudReglasService.validarEstadoActual(solicitud, "PENDIENTE");

		Equipo equipo = equipoRepository.findById(request.getEquipoId())
				.orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
		solicitudReglasService.validarEquipoDisponibleParaSolicitud(equipo);
		solicitudReglasService.validarSinSuperposicion(
				equipo,
				request.getFechaRetiro(),
				request.getFechaDevolucion(),
				solicitud.getNumSolicitud()
		);

		solicitud.setEquipo(equipo);
		solicitud.setFechaRetiro(request.getFechaRetiro());
		solicitud.setFechaDevolucion(request.getFechaDevolucion());
		solicitud.setMotivo(request.getMotivo());

		return solicitudDtoMapper.toResponseDto(solicitudRepository.save(solicitud));
	}
}
