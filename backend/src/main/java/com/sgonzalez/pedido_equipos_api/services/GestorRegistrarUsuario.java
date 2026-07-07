package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.CrearUsuarioRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.models.Rol;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IRolRepository;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorRegistrarUsuario {

	private final IUsuarioRepository usuarioRepository;
	private final IRolRepository rolRepository;
	private final PasswordEncoder passwordEncoder;

	public GestorRegistrarUsuario(
			IUsuarioRepository usuarioRepository,
			IRolRepository rolRepository,
			PasswordEncoder passwordEncoder
	) {
		this.usuarioRepository = usuarioRepository;
		this.rolRepository = rolRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UsuarioResponseDto registrarUsuario(CrearUsuarioRequestDto request) {
		validarRequest(request);

		String emailNormalizado = request.getEmail().trim().toLowerCase();
		String nombreRol = normalizarRol(request.getRol());

		if (usuarioRepository.existsByEmail(emailNormalizado)) {
			throw new IllegalArgumentException("Ya existe un usuario registrado con ese email");
		}

		Rol rol = rolRepository.findById(nombreRol)
				.orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

		Usuario usuario = new Usuario(
				request.getNombre().trim(),
				emailNormalizado,
				passwordEncoder.encode(request.getContrasena()),
				rol,
				request.getActivo()
		);

		Usuario usuarioGuardado = usuarioRepository.save(usuario);
		return toResponseDto(usuarioGuardado);
	}

	private void validarRequest(CrearUsuarioRequestDto request) {
		if (request == null) {
			throw new IllegalArgumentException("Los datos del usuario son obligatorios");
		}

		if (esBlanco(request.getNombre())) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}

		if (esBlanco(request.getEmail())) {
			throw new IllegalArgumentException("El email es obligatorio");
		}

		if (esBlanco(request.getContrasena())) {
			throw new IllegalArgumentException("La contrasena es obligatoria");
		}

		if (esBlanco(request.getRol())) {
			throw new IllegalArgumentException("El rol es obligatorio");
		}
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
	}

	private String normalizarRol(String rol) {
		return rol.trim().toUpperCase();
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
