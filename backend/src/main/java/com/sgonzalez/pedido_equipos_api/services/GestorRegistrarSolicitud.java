package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.CrearSolicitudRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.models.Estado;
import com.sgonzalez.pedido_equipos_api.models.HistorialSolicitud;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IEstadoRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IHistorialSolicitudRepository;
import com.sgonzalez.pedido_equipos_api.repositories.ISolicitudRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorRegistrarSolicitud {

    private final ISolicitudRepository solicitudRepository;
    private final IEquipoRepository equipoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IEstadoRepository estadoRepository;
    private final IHistorialSolicitudRepository historialSolicitudRepository;
    private final SolicitudDtoMapper solicitudDtoMapper;

    public GestorRegistrarSolicitud(
            ISolicitudRepository solicitudRepository,
            IEquipoRepository equipoRepository,
            IUsuarioRepository usuarioRepository,
            IEstadoRepository estadoRepository,
            IHistorialSolicitudRepository historialSolicitudRepository,
            SolicitudDtoMapper solicitudDtoMapper
    ) {
        this.solicitudRepository = solicitudRepository;
        this.equipoRepository = equipoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estadoRepository = estadoRepository;
        this.historialSolicitudRepository = historialSolicitudRepository;
        this.solicitudDtoMapper = solicitudDtoMapper;
    }

    @Transactional
    public SolicitudResponseDto registrarSolicitud(CrearSolicitudRequestDto request) {
        validarFechas(request);

        Equipo equipo = equipoRepository.findById(request.getEquipoId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
        Usuario solicitante = usuarioRepository.findById(request.getSolicitanteId())
                .orElseThrow(() -> new IllegalArgumentException("Solicitante no encontrado"));
        validarEquipoDisponibleParaSolicitud(equipo);
        validarSinSuperposicion(equipo, request.getFechaRetiro(), request.getFechaDevolucion(), null);

        Estado estadoInicial = estadoSolicitud(Boolean.TRUE.equals(equipo.getRequiereAutorizacion()) ? "PENDIENTE" : "APROBADA");

        Solicitud solicitud = new Solicitud(
                equipo,
                solicitante,
                request.getFechaRetiro(),
                request.getFechaDevolucion(),
                request.getMotivo()
        );
        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        if ("APROBADA".equals(estadoInicial.getNombre())) {
            equipo.setEstado(estadoEquipo("PRESTADO"));
            equipoRepository.save(equipo);
        }

        HistorialSolicitud historial = new HistorialSolicitud(
                solicitudGuardada,
                LocalDateTime.now(),
                null,
                solicitante,
                null,
                estadoInicial
        );
        historialSolicitudRepository.save(historial);

        return solicitudDtoMapper.toResponseDto(solicitudGuardada);
    }

    private void validarFechas(CrearSolicitudRequestDto request) {
        if (request.getFechaRetiro() == null || request.getFechaDevolucion() == null) {
            throw new IllegalArgumentException("Las fechas de retiro y devolucion son obligatorias");
        }

        if (request.getFechaDevolucion().isBefore(request.getFechaRetiro())) {
            throw new IllegalArgumentException("La fecha de devolucion no puede ser anterior a la fecha de retiro");
        }
    }

    private void validarEquipoDisponibleParaSolicitud(Equipo equipo) {
        String estadoEquipo = equipo.getEstado().getNombre();

        if ("MANTENIMIENTO".equals(estadoEquipo) || "DE_BAJA".equals(estadoEquipo)) {
            throw new IllegalStateException("El equipo no se encuentra disponible para solicitudes");
        }
    }

    private void validarSinSuperposicion(Equipo equipo, java.time.LocalDate fechaRetiro, java.time.LocalDate fechaDevolucion, Long solicitudExcluidaId) {
        boolean existeSuperposicion = solicitudRepository.findByEquipoId(equipo.getId())
                .stream()
                .filter(s -> solicitudExcluidaId == null || !s.getNumSolicitud().equals(solicitudExcluidaId))
                .filter(s -> "APROBADA".equals(estadoActual(s).getNombre()))
                .anyMatch(s -> fechasSuperpuestas(fechaRetiro, fechaDevolucion, s.getFechaRetiro(), s.getFechaDevolucion()));

        if (existeSuperposicion) {
            throw new IllegalStateException("Ya existe una solicitud aprobada superpuesta para ese equipo");
        }
    }

    private boolean fechasSuperpuestas(
            java.time.LocalDate inicioA,
            java.time.LocalDate finA,
            java.time.LocalDate inicioB,
            java.time.LocalDate finB
    ) {
        return !finA.isBefore(inicioB) && !inicioA.isAfter(finB);
    }

    private Estado estadoActual(Solicitud solicitud) {
        return historialSolicitudRepository.findBySolicitudNumSolicitudAndFechaHoraFinIsNull(solicitud.getNumSolicitud())
                .orElseThrow(() -> new IllegalStateException("La solicitud no tiene un estado vigente"))
                .getEstado();
    }

    private Estado estadoSolicitud(String nombre) {
        return estadoRepository.findByNombreAndAmbito(nombre, "SOLICITUD")
                .orElseThrow(() -> new IllegalStateException("Estado de solicitud no configurado: " + nombre));
    }

    private Estado estadoEquipo(String nombre) {
        return estadoRepository.findByNombreAndAmbito(nombre, "EQUIPO")
                .orElseThrow(() -> new IllegalStateException("Estado de equipo no configurado: " + nombre));
    }

}
