package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.EquipoFiltroDto;
import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Equipo;
import com.sgonzalez.pedido_equipos_api.repositories.IEquipoRepository;
import jakarta.persistence.criteria.JoinType;
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
public class GestorConsultarEquipo {

	private final IEquipoRepository equipoRepository;

	public GestorConsultarEquipo(IEquipoRepository equipoRepository) {
		this.equipoRepository = equipoRepository;
	}

	public List<EquipoResponseDto> consultarEquipos() {
		return equipoRepository.findAll()
				.stream()
				.map(this::toResponseDto)
				.toList();
	}

	public Page<EquipoResponseDto> consultarEquipos(EquipoFiltroDto filtros) {
		EquipoFiltroDto filtrosNormalizados = filtros == null ? new EquipoFiltroDto() : filtros;
		Pageable pageable = crearPageable(
				filtrosNormalizados.getPage(),
				filtrosNormalizados.getSize(),
				filtrosNormalizados.getSortBy(),
				filtrosNormalizados.getSortDirection()
		);

		return equipoRepository.findAll(crearSpecification(filtrosNormalizados), pageable)
				.map(this::toResponseDto);
	}

	public EquipoResponseDto consultarEquipo(Long id) {
		Equipo equipo = equipoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
		return toResponseDto(equipo);
	}

	EquipoResponseDto toResponseDto(Equipo equipo) {
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

	private Specification<Equipo> crearSpecification(EquipoFiltroDto filtros) {
		return (root, query, criteriaBuilder) -> {
			List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

			if (tieneTexto(filtros.getSearch())) {
				String search = like(filtros.getSearch());
				predicates.add(criteriaBuilder.or(
						criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoInventario")), search),
						criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), search),
						criteriaBuilder.like(criteriaBuilder.lower(root.get("categoria")), search),
						criteriaBuilder.like(criteriaBuilder.lower(root.get("ubicacion")), search),
						criteriaBuilder.like(criteriaBuilder.lower(root.join("estado", JoinType.LEFT).get("nombre")), search)
				));
			}

			if (tieneTexto(filtros.getCodigoInventario())) {
				predicates.add(criteriaBuilder.like(
						criteriaBuilder.lower(root.get("codigoInventario")),
						like(filtros.getCodigoInventario())
				));
			}

			if (tieneTexto(filtros.getNombre())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), like(filtros.getNombre())));
			}

			if (tieneTexto(filtros.getCategoria())) {
				predicates.add(criteriaBuilder.equal(
						criteriaBuilder.lower(root.get("categoria")),
						normalizar(filtros.getCategoria())
				));
			}

			if (tieneTexto(filtros.getEstado())) {
				predicates.add(criteriaBuilder.equal(
						criteriaBuilder.lower(root.join("estado", JoinType.LEFT).get("nombre")),
						normalizar(filtros.getEstado())
				));
			}

			if (tieneTexto(filtros.getUbicacion())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ubicacion")), like(filtros.getUbicacion())));
			}

			if (filtros.getRequiereAutorizacion() != null) {
				predicates.add(criteriaBuilder.equal(root.get("requiereAutorizacion"), filtros.getRequiereAutorizacion()));
			}

			return criteriaBuilder.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
		};
	}

	private Pageable crearPageable(Integer page, Integer size, String sortBy, String sortDirection) {
		int pageNormalizada = page == null || page < 0 ? 0 : page;
		int sizeNormalizado = size == null || size <= 0 ? 10 : Math.min(size, 100);
		String sortByNormalizado = normalizarSortBy(sortBy);
		Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;

		return PageRequest.of(pageNormalizada, sizeNormalizado, Sort.by(direction, sortByNormalizado));
	}

	private String normalizarSortBy(String sortBy) {
		Map<String, String> sortPermitido = Map.of(
				"id", "id",
				"codigoInventario", "codigoInventario",
				"nombre", "nombre",
				"categoria", "categoria",
				"estado", "estado.nombre",
				"ubicacion", "ubicacion",
				"requiereAutorizacion", "requiereAutorizacion"
		);

		if (!tieneTexto(sortBy)) {
			return "id";
		}

		return sortPermitido.getOrDefault(sortBy, "id");
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
