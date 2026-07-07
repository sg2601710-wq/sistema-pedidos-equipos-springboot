package com.sgonzalez.pedido_equipos_api.dtos;

public class ApiResponseDto<T> {

	private Boolean success;
	private String message;
	private T data;
	private Object meta;
	private ApiErrorDto error;

	public ApiResponseDto() {
	}

	public ApiResponseDto(Boolean success, String message, T data, Object meta, ApiErrorDto error) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.meta = meta;
		this.error = error;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Object getMeta() {
		return meta;
	}

	public void setMeta(Object meta) {
		this.meta = meta;
	}

	public ApiErrorDto getError() {
		return error;
	}

	public void setError(ApiErrorDto error) {
		this.error = error;
	}

	public static class ApiErrorDto {

		private String code;
		private Object details;

		public ApiErrorDto() {
		}

		public ApiErrorDto(String code, Object details) {
			this.code = code;
			this.details = details;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Object getDetails() {
			return details;
		}

		public void setDetails(Object details) {
			this.details = details;
		}
	}
}
