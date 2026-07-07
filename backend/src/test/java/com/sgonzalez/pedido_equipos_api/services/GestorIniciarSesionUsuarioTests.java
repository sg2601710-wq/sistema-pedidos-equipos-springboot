package com.sgonzalez.pedido_equipos_api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sgonzalez.pedido_equipos_api.dtos.IniciarSesionRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.IniciarSesionResponseDto;
import com.sgonzalez.pedido_equipos_api.exceptions.CredencialesInvalidasException;
import com.sgonzalez.pedido_equipos_api.exceptions.UsuarioInactivoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GestorIniciarSesionUsuarioTests {

	@Autowired
	private GestorIniciarSesionUsuario gestorIniciarSesionUsuario;

	@Autowired
	private JwtService jwtService;

	@Test
	void iniciaSesionConCredencialesValidas() {
		IniciarSesionResponseDto response = gestorIniciarSesionUsuario.iniciarSesion(
				new IniciarSesionRequestDto("admin@pedidos.local", "password")
		);

		assertThat(response.getUsuario().getEmail()).isEqualTo("admin@pedidos.local");
		assertThat(response.getUsuario().getRol()).isEqualTo("ADMIN");
		assertThat(response.getToken()).isNotBlank();
		assertThat(response.getToken().split("\\.")).hasSize(3);
		assertThat(jwtService.esTokenValido(response.getToken())).isTrue();
	}

	@Test
	void rechazaContrasenaIncorrecta() {
		assertThatThrownBy(() -> gestorIniciarSesionUsuario.iniciarSesion(
				new IniciarSesionRequestDto("admin@pedidos.local", "incorrecta")
		)).isInstanceOf(CredencialesInvalidasException.class);
	}

	@Test
	void rechazaUsuarioInactivo() {
		assertThatThrownBy(() -> gestorIniciarSesionUsuario.iniciarSesion(
				new IniciarSesionRequestDto("inactivo@pedidos.local", "password")
		)).isInstanceOf(UsuarioInactivoException.class);
	}
}
