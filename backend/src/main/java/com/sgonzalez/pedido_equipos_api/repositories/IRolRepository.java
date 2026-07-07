package com.sgonzalez.pedido_equipos_api.repositories;

import com.sgonzalez.pedido_equipos_api.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, String> {
}
