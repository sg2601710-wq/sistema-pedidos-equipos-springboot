package com.sgonzalez.pedido_equipos_api.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.sgonzalez.pedido_equipos_api.dtos.SolicitudResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GestorConsultarSolicitudTests {

	@Autowired
	private GestorConsultarSolicitud gestorConsultarSolicitud;

	@Test
	void adminPuedeConsultarTodasLasSolicitudes() {
		List<SolicitudResponseDto> solicitudes = gestorConsultarSolicitud.consultarSolicitudes(1L);

		assertThat(solicitudes).hasSize(3);
	}
}
