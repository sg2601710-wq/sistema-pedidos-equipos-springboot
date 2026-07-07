package com.sgonzalez.pedido_equipos_api.repositories;

import com.sgonzalez.pedido_equipos_api.models.Estado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadoRepository extends JpaRepository<Estado, String> {

	List<Estado> findByAmbito(String ambito);

	Optional<Estado> findByNombreAndAmbito(String nombre, String ambito);
}
