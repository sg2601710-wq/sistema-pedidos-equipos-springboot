package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.IniciarSesionRequestDto;
import com.sgonzalez.pedido_equipos_api.dtos.IniciarSesionResponseDto;
import com.sgonzalez.pedido_equipos_api.dtos.UsuarioResponseDto;
import com.sgonzalez.pedido_equipos_api.exceptions.CredencialesInvalidasException;
import com.sgonzalez.pedido_equipos_api.exceptions.UsuarioInactivoException;
import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GestorIniciarSesionUsuario {

	private final IUsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public GestorIniciarSesionUsuario(
			IUsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService
	) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public IniciarSesionResponseDto iniciarSesion(IniciarSesionRequestDto request) {
		validarRequest(request);

		String emailNormalizado = request.getEmail().trim().toLowerCase();
		Usuario usuario = usuarioRepository.findByEmail(emailNormalizado)
				.orElseThrow(() -> new CredencialesInvalidasException("Credenciales invalidas"));

		if (!Boolean.TRUE.equals(usuario.getActivo())) {
			throw new UsuarioInactivoException("El usuario se encuentra inactivo");
		}

		if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasenaHash())) {
			throw new CredencialesInvalidasException("Credenciales invalidas");
		}

		String token = jwtService.generarToken(usuario);
		return new IniciarSesionResponseDto(toResponseDto(usuario), token);
	}

	private void validarRequest(IniciarSesionRequestDto request) {
		if (request == null) {
			throw new IllegalArgumentException("Las credenciales son obligatorias");
		}

		if (esBlanco(request.getEmail())) {
			throw new IllegalArgumentException("El email es obligatorio");
		}

		if (esBlanco(request.getContrasena())) {
			throw new IllegalArgumentException("La contrasena es obligatoria");
		}
	}

	private boolean esBlanco(String value) {
		return value == null || value.trim().isEmpty();
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
