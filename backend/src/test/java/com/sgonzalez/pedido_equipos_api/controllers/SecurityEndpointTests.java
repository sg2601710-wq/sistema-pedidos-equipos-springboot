package com.sgonzalez.pedido_equipos_api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityEndpointTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void rechazaEndpointProtegidoSinToken() throws Exception {
		mockMvc.perform(get("/equipos"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void permiteEndpointProtegidoConTokenValido() throws Exception {
		MvcResult loginResult = mockMvc.perform(post("/usuarios/sesion")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"email":"admin@pedidos.local","contrasena":"password"}\
								"""))
				.andExpect(status().isOk())
				.andReturn();

		String token = extraerToken(loginResult.getResponse().getContentAsString());

		mockMvc.perform(get("/equipos")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}

	@Test
	void consultaUsuarioActualConTokenValido() throws Exception {
		MvcResult loginResult = mockMvc.perform(post("/usuarios/sesion")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"email":"admin@pedidos.local","contrasena":"password"}\
								"""))
				.andExpect(status().isOk())
				.andReturn();

		String token = extraerToken(loginResult.getResponse().getContentAsString());

		MvcResult meResult = mockMvc.perform(get("/usuarios/me")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andReturn();

		assertThat(meResult.getResponse().getContentAsString())
				.contains("\"email\":\"admin@pedidos.local\"")
				.contains("\"rol\":\"ADMIN\"");
	}

	private String extraerToken(String responseBody) {
		Matcher matcher = Pattern.compile("\"token\"\\s*:\\s*\"([^\"]+)\"").matcher(responseBody);
		assertThat(matcher.find()).isTrue();
		return matcher.group(1);
	}
}
