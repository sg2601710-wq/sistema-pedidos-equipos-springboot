package com.sgonzalez.pedido_equipos_api.repositories;

import com.sgonzalez.pedido_equipos_api.models.Equipo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEquipoRepository extends JpaRepository<Equipo, Long>, JpaSpecificationExecutor<Equipo> {

	Optional<Equipo> findByCodigoInventario(String codigoInventario);

	boolean existsByCodigoInventario(String codigoInventario);
}
