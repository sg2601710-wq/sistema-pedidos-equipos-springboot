package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorDesactivarUsuario {

	private final IUsuarioRepository usuarioRepository;
	private final GestorConsultarUsuario gestorConsultarUsuario;

	public GestorDesactivarUsuario(IUsuarioRepository usuarioRepository, GestorConsultarUsuario gestorConsultarUsuario) {
		this.usuarioRepository = usuarioRepository;
		this.gestorConsultarUsuario = gestorConsultarUsuario;
	}

	@Transactional
	public UsuarioResponseDto desactivarUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		usuario.setActivo(false);
		return gestorConsultarUsuario.toResponseDto(usuarioRepository.save(usuario));
	}
}
