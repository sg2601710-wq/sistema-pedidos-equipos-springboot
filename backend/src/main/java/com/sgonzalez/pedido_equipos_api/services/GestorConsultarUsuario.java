package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GestorConsultarUsuario {

	private final IUsuarioRepository usuarioRepository;

	public GestorConsultarUsuario(IUsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public List<UsuarioResponseDto> consultarUsuarios() {
		return usuarioRepository.findAll().stream().map(this::toResponseDto).toList();
	}

	public UsuarioResponseDto consultarUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		return toResponseDto(usuario);
	}

	UsuarioResponseDto toResponseDto(Usuario usuario) {
		return new UsuarioResponseDto(
				usuario.getId(),
				usuario.getNombre(),
				usuario.getEmail(),
				usuario.getRol().getNombre(),
				usuario.getActivo()
		);
	}
}
