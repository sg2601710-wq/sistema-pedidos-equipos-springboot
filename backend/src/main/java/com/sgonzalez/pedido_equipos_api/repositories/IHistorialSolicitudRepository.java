package com.sgonzalez.pedido_equipos_api.repositories;

import com.sgonzalez.pedido_equipos_api.models.HistorialSolicitud;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistorialSolicitudRepository extends JpaRepository<HistorialSolicitud, Long> {

	List<HistorialSolicitud> findBySolicitudNumSolicitudOrderByFechaHoraInicioAsc(Long numSolicitud);

	Optional<HistorialSolicitud> findBySolicitudNumSolicitudAndFechaHoraFinIsNull(Long numSolicitud);
}
