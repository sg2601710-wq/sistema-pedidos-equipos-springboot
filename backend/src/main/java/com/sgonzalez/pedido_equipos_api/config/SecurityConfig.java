package com.sgonzalez.pedido_equipos_api.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/usuarios/sesion").permitAll()
						.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
						.requestMatchers(HttpMethod.GET, "/health-check").permitAll()
						.requestMatchers(HttpMethod.POST, "/equipos", "/estados", "/roles").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/equipos/**", "/usuarios/**", "/estados/**", "/roles/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/equipos/**", "/usuarios/**", "/estados/**", "/roles/**").hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, authException) -> escribirError(
								response,
								HttpServletResponse.SC_UNAUTHORIZED,
								"UNAUTHORIZED",
								"Token ausente o invalido"
						))
						.accessDeniedHandler((request, response, accessDeniedException) -> escribirError(
								response,
								HttpServletResponse.SC_FORBIDDEN,
								"FORBIDDEN",
								"No tenes permisos para realizar esta operacion"
						))
				)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			throw new UsernameNotFoundException("La autenticacion se realiza mediante JWT");
		};
	}

	private void escribirError(
			HttpServletResponse response,
			int status,
			String code,
			String message
	) throws java.io.IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("""
				{"success":false,"message":"%s","data":null,"meta":null,"error":{"code":"%s","details":null}}\
				""".formatted(message, code));
	}
}
