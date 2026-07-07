package com.sgonzalez.pedido_equipos_api.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.sgonzalez.pedido_equipos_api.dtos.EquipoResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GestorConsultarEquipoTests {

	@Autowired
	private GestorConsultarEquipo gestorConsultarEquipo;

	@Test
	void consultaTodosLosEquipos() {
		List<EquipoResponseDto> equipos = gestorConsultarEquipo.consultarEquipos();

		assertThat(equipos).hasSize(5);
		assertThat(equipos)
				.extracting(EquipoResponseDto::getCodigoInventario)
				.contains("EQ-0001", "EQ-0002", "EQ-0003", "EQ-0004", "EQ-0005");
	}
}
