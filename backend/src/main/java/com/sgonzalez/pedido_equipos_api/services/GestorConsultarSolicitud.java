package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.SolicitudFiltroDto;
import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import com.sgonzalez.pedido_equipos_api.models.HistorialSolicitud;
import com.sgonzalez.pedido_equipos_api.models.Solicitud;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.ISolicitudRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

	public Page<SolicitudResponseDto> consultarSolicitudes(SolicitudFiltroDto filtros) {
		SolicitudFiltroDto filtrosNormalizados = filtros == null ? new SolicitudFiltroDto() : filtros;

		if (filtrosNormalizados.getUsuarioId() == null) {
			throw new IllegalArgumentException("El usuario es obligatorio para consultar solicitudes");
		}

		Usuario usuario = usuarioRepository.findById(filtrosNormalizados.getUsuarioId())
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

		Pageable pageable = crearPageable(
				filtrosNormalizados.getPage(),
				filtrosNormalizados.getSize(),
				filtrosNormalizados.getSortBy(),
				filtrosNormalizados.getSortDirection()
		);

		return solicitudRepository.findAll(crearSpecification(filtrosNormalizados, usuario), pageable)
				.map(solicitudDtoMapper::toResponseDto);
	}

	private boolean puedeVerTodasLasSolicitudes(Usuario usuario) {
		String rol = usuario.getRol().getNombre();
		return "ADMIN".equals(rol) || "ENCARGADO".equals(rol);
	}

	private Specification<Solicitud> crearSpecification(SolicitudFiltroDto filtros, Usuario usuario) {
		return (root, query, criteriaBuilder) -> {
			if (query != null) {
				query.distinct(true);
			}

			List<Predicate> predicates = new ArrayList<>();

			if (!puedeVerTodasLasSolicitudes(usuario)) {
				predicates.add(criteriaBuilder.equal(root.get("solicitante").get("id"), usuario.getId()));
			} else if (filtros.getSolicitanteId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("solicitante").get("id"), filtros.getSolicitanteId()));
			}

			if (filtros.getNumSolicitud() != null) {
				predicates.add(criteriaBuilder.equal(root.get("numSolicitud"), filtros.getNumSolicitud()));
			}

			if (filtros.getEquipoId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("equipo").get("id"), filtros.getEquipoId()));
			}

			if (tieneTexto(filtros.getCodigoInventario())) {
				predicates.add(criteriaBuilder.like(
						criteriaBuilder.lower(root.get("equipo").get("codigoInventario")),
						like(filtros.getCodigoInventario())
				));
			}

			if (tieneTexto(filtros.getEstado())) {
				Join<Solicitud, HistorialSolicitud> historial = root.join("historialSolicitud", JoinType.INNER);
				predicates.add(criteriaBuilder.isNull(historial.get("fechaHoraFin")));
				predicates.add(criteriaBuilder.equal(
						criteriaBuilder.lower(historial.get("estado").get("nombre")),
						normalizar(filtros.getEstado())
				));
			}

			if (filtros.getFechaRetiroDesde() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaRetiro"), filtros.getFechaRetiroDesde()));
			}

			if (filtros.getFechaRetiroHasta() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaRetiro"), filtros.getFechaRetiroHasta()));
			}

			if (filtros.getFechaDevolucionDesde() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaDevolucion"), filtros.getFechaDevolucionDesde()));
			}

			if (filtros.getFechaDevolucionHasta() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaDevolucion"), filtros.getFechaDevolucionHasta()));
			}

			if (tieneTexto(filtros.getMotivo())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("motivo")), like(filtros.getMotivo())));
			}

			return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
		};
	}

	private Pageable crearPageable(Integer page, Integer size, String sortBy, String sortDirection) {
		int pageNormalizada = page == null || page < 0 ? 0 : page;
		int sizeNormalizado = size == null || size <= 0 ? 10 : Math.min(size, 100);
		String sortByNormalizado = normalizarSortBy(sortBy);
		Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;

		return PageRequest.of(pageNormalizada, sizeNormalizado, Sort.by(direction, sortByNormalizado));
	}

	private String normalizarSortBy(String sortBy) {
		Map<String, String> sortPermitido = Map.of(
				"numSolicitud", "numSolicitud",
				"fechaRetiro", "fechaRetiro",
				"fechaDevolucion", "fechaDevolucion",
				"motivo", "motivo",
				"equipo", "equipo.nombre",
				"solicitante", "solicitante.nombre"
		);

		if (!tieneTexto(sortBy)) {
			return "numSolicitud";
		}

		return sortPermitido.getOrDefault(sortBy.trim(), "numSolicitud");
	}

	private boolean tieneTexto(String value) {
		return value != null && !value.trim().isEmpty();
	}

	private String like(String value) {
		return "%" + normalizar(value) + "%";
	}

	private String normalizar(String value) {
		return value.trim().toLowerCase();
	}
}
