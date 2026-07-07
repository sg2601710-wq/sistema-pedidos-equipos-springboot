package com.sgonzalez.pedido_equipos_api.dtos;

import java.time.Instant;
import java.util.Map;

public class HealthCheckResponseDto {

	private String status;
	private String application;
	private String environment;
	private Instant timestamp;
	private long uptimeSeconds;
	private Map<String, String> runtime;
	private Map<String, String> checks;

	public HealthCheckResponseDto() {
	}

	public HealthCheckResponseDto(
			String status,
			String application,
			String environment,
			Instant timestamp,
			long uptimeSeconds,
			Map<String, String> runtime,
			Map<String, String> checks
	) {
		this.status = status;
		this.application = application;
		this.environment = environment;
		this.timestamp = timestamp;
		this.uptimeSeconds = uptimeSeconds;
		this.runtime = runtime;
		this.checks = checks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public long getUptimeSeconds() {
		return uptimeSeconds;
	}

	public void setUptimeSeconds(long uptimeSeconds) {
		this.uptimeSeconds = uptimeSeconds;
	}

	public Map<String, String> getRuntime() {
		return runtime;
	}

	public void setRuntime(Map<String, String> runtime) {
		this.runtime = runtime;
	}

	public Map<String, String> getChecks() {
		return checks;
	}

	public void setChecks(Map<String, String> checks) {
		this.checks = checks;
	}
}
