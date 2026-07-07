package com.sgonzalez.pedido_equipos_api.exceptions;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;
import com.sgonzalez.pedido_equipos_api.utils.ApiResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarErroresValidacion(MethodArgumentNotValidException ex) {
		Map<String, String> errores = new LinkedHashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error ->
				errores.put(error.getField(), error.getDefaultMessage())
		);

		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error("Los datos enviados no son validos", "VALIDATION_ERROR", errores));
	}

	@ExceptionHandler(CredencialesInvalidasException.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarCredencialesInvalidas(CredencialesInvalidasException ex) {
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ApiResponse.error(ex.getMessage(), "UNAUTHORIZED"));
	}

	@ExceptionHandler(UsuarioInactivoException.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarUsuarioInactivo(UsuarioInactivoException ex) {
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(ApiResponse.error(ex.getMessage(), "FORBIDDEN"));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarBadRequest(IllegalArgumentException ex) {
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(ex.getMessage(), "BAD_REQUEST"));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarConflict(IllegalStateException ex) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(ApiResponse.error(ex.getMessage(), "CONFLICT"));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error("El cuerpo de la peticion no tiene un formato valido", "MALFORMED_JSON"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto<Object>> manejarErrorNoControlado(Exception ex) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Ocurrio un error inesperado", "INTERNAL_ERROR"));
	}
}
