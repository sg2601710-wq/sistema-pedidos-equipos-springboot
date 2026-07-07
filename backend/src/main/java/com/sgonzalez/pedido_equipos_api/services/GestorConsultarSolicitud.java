package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.ISolicitudRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GestorConsultarSolicitud {

	private final ISolicitudRepository solicitudRepository;
	private final IUsuarioRepository usuarioRepository;
	private final SolicitudDtoMapper solicitudDtoMapper;

	public GestorConsultarSolicitud(
			ISolicitudRepository solicitudRepository,
			IUsuarioRepository usuarioRepository,
			SolicitudDtoMapper solicitudDtoMapper
	) {
		this.solicitudRepository = solicitudRepository;
		this.usuarioRepository = usuarioRepository;
		this.solicitudDtoMapper = solicitudDtoMapper;
	}

	public List<SolicitudResponseDto> consultarSolicitudes(Long usuarioId) {
		if (usuarioId == null) {
			throw new IllegalArgumentException("El usuario es obligatorio para consultar solicitudes");
		}

		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

		List<Solicitud> solicitudes = puedeVerTodasLasSolicitudes(usuario)
				? solicitudRepository.findAll()
				: solicitudRepository.findBySolicitanteId(usuario.getId());

		return solicitudes.stream()
				.map(solicitudDtoMapper::toResponseDto)
				.toList();
	}

	private boolean puedeVerTodasLasSolicitudes(Usuario usuario) {
		String rol = usuario.getRol().getNombre();
		return "ADMIN".equals(rol) || "ENCARGADO".equals(rol);
	}
}
