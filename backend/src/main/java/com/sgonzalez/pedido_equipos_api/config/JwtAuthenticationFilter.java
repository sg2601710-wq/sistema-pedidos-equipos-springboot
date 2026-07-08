package com.sgonzalez.pedido_equipos_api.config;

import com.sgonzalez.pedido_equipos_api.models.Usuario;
import com.sgonzalez.pedido_equipos_api.repositories.IUsuarioRepository;
import com.sgonzalez.pedido_equipos_api.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtService jwtService;
	private final IUsuarioRepository usuarioRepository;

	public JwtAuthenticationFilter(JwtService jwtService, IUsuarioRepository usuarioRepository) {
		this.jwtService = jwtService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.equals("/api/docs")
				|| path.equals("/api/docs/")
				|| path.startsWith("/api/docs/")
				|| path.startsWith("/api/swagger-ui/")
				|| path.startsWith("/swagger-ui/")
				|| path.equals("/v3/api-docs")
				|| path.startsWith("/v3/api-docs/")
				|| path.startsWith("/webjars/")
				|| path.equals("/health-check")
				|| path.equals("/usuarios/sesion");
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		String token = obtenerToken(request);

		if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			autenticar(token);
		}

		filterChain.doFilter(request, response);
	}

	private String obtenerToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
			return null;
		}

		return authorizationHeader.substring(BEARER_PREFIX.length());
	}

	private void autenticar(String token) {
		if (!jwtService.esTokenValido(token)) {
			return;
		}

		Map<String, Object> claims = jwtService.obtenerClaims(token);
		String email = (String) claims.get("sub");

		if (email == null || email.isBlank()) {
			return;
		}

		Usuario usuario = usuarioRepository.findByEmail(email)
				.filter(u -> Boolean.TRUE.equals(u.getActivo()))
				.orElse(null);

		if (usuario == null) {
			return;
		}

		String rol = "ROLE_" + usuario.getRol().getNombre();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				usuario.getEmail(),
				null,
				List.of(new SimpleGrantedAuthority(rol))
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
