package com.sgonzalez.pedido_equipos_api.utils;

import com.sgonzalez.pedido_equipos_api.dtos.ApiResponseDto;

public final class ApiResponse {

	private ApiResponse() {
	}

	public static <T> ApiResponseDto<T> success(String message, T data) {
		return success(message, data, null);
	}

	public static <T> ApiResponseDto<T> success(String message, T data, Object meta) {
		return new ApiResponseDto<>(true, message, data, meta, null);
	}

	public static ApiResponseDto<Object> error(String message, String code) {
		return error(message, code, null);
	}

	public static ApiResponseDto<Object> error(String message, String code, Object details) {
		return new ApiResponseDto<>(
				false,
				message,
				null,
				null,
				new ApiResponseDto.ApiErrorDto(code, details)
		);
	}
}
