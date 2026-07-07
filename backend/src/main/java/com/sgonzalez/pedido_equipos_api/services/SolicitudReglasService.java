package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.models.HistorialSolicitud;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IHistorialSolicitudRepository;
import com.sgonzalez.pedido_equipos_api.repositories.ISolicitudRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class SolicitudReglasService {

	private final ISolicitudRepository solicitudRepository;
	private final IUsuarioRepository usuarioRepository;
	private final IEstadoRepository estadoRepository;
	private final IHistorialSolicitudRepository historialSolicitudRepository;

	public SolicitudReglasService(
			ISolicitudRepository solicitudRepository,
			IUsuarioRepository usuarioRepository,
			IEstadoRepository estadoRepository,
			IHistorialSolicitudRepository historialSolicitudRepository
	) {
		this.solicitudRepository = solicitudRepository;
		this.usuarioRepository = usuarioRepository;
		this.estadoRepository = estadoRepository;
		this.historialSolicitudRepository = historialSolicitudRepository;
	}

	public Solicitud buscarSolicitud(Long numSolicitud) {
		return solicitudRepository.findById(numSolicitud)
				.orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
	}

	public Usuario buscarUsuarioActivo(String email) {
		return usuarioRepository.findByEmail(email)
				.filter(u -> Boolean.TRUE.equals(u.getActivo()))
				.orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));
	}

	public void validarFechas(LocalDate fechaRetiro, LocalDate fechaDevolucion) {
		if (fechaRetiro == null || fechaDevolucion == null) {
			throw new IllegalArgumentException("Las fechas de retiro y devolucion son obligatorias");
		}

		if (fechaDevolucion.isBefore(fechaRetiro)) {
			throw new IllegalArgumentException("La fecha de devolucion no puede ser anterior a la fecha de retiro");
		}
	}

	public void validarEquipoDisponibleParaSolicitud(Equipo equipo) {
		String estadoEquipo = equipo.getEstado().getNombre();

		if ("MANTENIMIENTO".equals(estadoEquipo) || "DE_BAJA".equals(estadoEquipo)) {
			throw new IllegalStateException("El equipo no se encuentra disponible para solicitudes");
		}
	}

	public void validarEstadoActual(Solicitud solicitud, String estadoEsperado) {
		String estadoActual = estadoActual(solicitud).getNombre();

		if (!estadoEsperado.equals(estadoActual)) {
			throw new IllegalStateException("La solicitud debe estar en estado " + estadoEsperado);
		}
	}

	public void validarPropietarioOPrivilegiado(Solicitud solicitud, Usuario usuario) {
		if (solicitud.getSolicitante().getId().equals(usuario.getId()) || esAdminOEncargado(usuario)) {
			return;
		}

		throw new IllegalStateException("No tenes permisos para modificar esta solicitud");
	}

	public void validarAdminOEncargado(Usuario usuario) {
		if (!esAdminOEncargado(usuario)) {
			throw new IllegalStateException("Solo un admin o encargado puede realizar esta operacion");
		}
	}

	public boolean esAdminOEncargado(Usuario usuario) {
		String rol = usuario.getRol().getNombre();
		return "ADMIN".equals(rol) || "ENCARGADO".equals(rol);
	}

	public void validarSinSuperposicion(
			Equipo equipo,
			LocalDate fechaRetiro,
			LocalDate fechaDevolucion,
			Long solicitudExcluidaId
	) {
		boolean existeSuperposicion = solicitudRepository.findByEquipoId(equipo.getId())
				.stream()
				.filter(s -> solicitudExcluidaId == null || !s.getNumSolicitud().equals(solicitudExcluidaId))
				.filter(s -> "APROBADA".equals(estadoActual(s).getNombre()))
				.anyMatch(s -> fechasSuperpuestas(fechaRetiro, fechaDevolucion, s.getFechaRetiro(), s.getFechaDevolucion()));

		if (existeSuperposicion) {
			throw new IllegalStateException("Ya existe una solicitud aprobada superpuesta para ese equipo");
		}
	}

	public Estado estadoActual(Solicitud solicitud) {
		return historialSolicitudRepository.findBySolicitudNumSolicitudAndFechaHoraFinIsNull(solicitud.getNumSolicitud())
				.orElseThrow(() -> new IllegalStateException("La solicitud no tiene un estado vigente"))
				.getEstado();
	}

	public void cambiarEstadoSolicitud(Solicitud solicitud, Usuario responsable, String nombreNuevoEstado) {
		LocalDateTime ahora = LocalDateTime.now();
		HistorialSolicitud historialActual = historialSolicitudRepository
				.findBySolicitudNumSolicitudAndFechaHoraFinIsNull(solicitud.getNumSolicitud())
				.orElseThrow(() -> new IllegalStateException("La solicitud no tiene un estado vigente"));
		Estado estadoAnterior = historialActual.getEstado();
		Estado nuevoEstado = estadoSolicitud(nombreNuevoEstado);

		historialActual.setFechaHoraFin(ahora);
		historialSolicitudRepository.save(historialActual);

		historialSolicitudRepository.save(new HistorialSolicitud(
				solicitud,
				ahora,
				null,
				responsable,
				estadoAnterior,
				nuevoEstado
		));
	}

	public Estado estadoEquipo(String nombre) {
		return estadoRepository.findByNombreAndAmbito(nombre, "EQUIPO")
				.orElseThrow(() -> new IllegalStateException("Estado de equipo no configurado: " + nombre));
	}

	private Estado estadoSolicitud(String nombre) {
		return estadoRepository.findByNombreAndAmbito(nombre, "SOLICITUD")
				.orElseThrow(() -> new IllegalStateException("Estado de solicitud no configurado: " + nombre));
	}

	private boolean fechasSuperpuestas(LocalDate inicioA, LocalDate finA, LocalDate inicioB, LocalDate finB) {
		return !finA.isBefore(inicioB) && !inicioA.isAfter(finB);
	}
}
