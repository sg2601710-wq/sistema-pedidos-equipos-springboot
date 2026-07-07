package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class GestorConsultarUsuarioActual {

	private final IUsuarioRepository usuarioRepository;

	public GestorConsultarUsuarioActual(IUsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public UsuarioResponseDto consultarUsuarioActual(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("No se pudo identificar al usuario autenticado");
		}

		Usuario usuario = usuarioRepository.findByEmail(email)
				.filter(u -> Boolean.TRUE.equals(u.getActivo()))
				.orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));

		return toResponseDto(usuario);
	}

	private UsuarioResponseDto toResponseDto(Usuario usuario) {
		return new UsuarioResponseDto(
				usuario.getId(),
				usuario.getNombre(),
				usuario.getEmail(),
				usuario.getRol().getNombre(),
				usuario.getActivo()
		);
	}
}
