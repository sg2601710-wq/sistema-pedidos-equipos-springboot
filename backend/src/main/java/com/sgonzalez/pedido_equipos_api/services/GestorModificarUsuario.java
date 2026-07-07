package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.ActualizarUsuarioRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Rol;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IRolRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorModificarUsuario {

	private final IUsuarioRepository usuarioRepository;
	private final IRolRepository rolRepository;
	private final GestorConsultarUsuario gestorConsultarUsuario;

	public GestorModificarUsuario(
			IUsuarioRepository usuarioRepository,
			IRolRepository rolRepository,
			GestorConsultarUsuario gestorConsultarUsuario
	) {
		this.usuarioRepository = usuarioRepository;
		this.rolRepository = rolRepository;
		this.gestorConsultarUsuario = gestorConsultarUsuario;
	}

	@Transactional
	public UsuarioResponseDto modificarUsuario(Long id, ActualizarUsuarioRequestDto request) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		String email = request.getEmail().trim().toLowerCase();

		usuarioRepository.findByEmail(email)
				.filter(u -> !u.getId().equals(id))
				.ifPresent(u -> {
					throw new IllegalArgumentException("Ya existe un usuario registrado con ese email");
				});

		Rol rol = rolRepository.findById(request.getRol().trim().toUpperCase())
				.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

		usuario.setNombre(request.getNombre().trim());
		usuario.setEmail(email);
		usuario.setRol(rol);
		usuario.setActivo(request.getActivo());

		return gestorConsultarUsuario.toResponseDto(usuarioRepository.save(usuario));
	}
}
