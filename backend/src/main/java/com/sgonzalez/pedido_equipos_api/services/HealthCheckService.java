package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.dtos.HealthCheckResponseDto;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

	private final DataSource dataSource;
	private final Environment environment;

	public HealthCheckService(DataSource dataSource, Environment environment) {
		this.dataSource = dataSource;
		this.environment = environment;
	}

	public HealthCheckResponseDto getStatus() {
		String databaseStatus = verificarBaseDeDatos();
		String status = "OK".equals(databaseStatus) ? "UP" : "DEGRADED";

		return new HealthCheckResponseDto(
				status,
				environment.getProperty("spring.application.name", "pedido-equipos-api"),
				obtenerAmbiente(),
				Instant.now(),
				ManagementFactory.getRuntimeMXBean().getUptime() / 1000,
				Map.of(
						"javaVersion", System.getProperty("java.version"),
						"platform", System.getProperty("os.name"),
						"pid", obtenerPid()
				),
				Map.of(
						"httpServer", "OK",
						"configuration", "OK",
						"database", databaseStatus
				)
		);
	}

	private String verificarBaseDeDatos() {
		try (Connection connection = dataSource.getConnection()) {
			return connection.isValid(2) ? "OK" : "ERROR";
		} catch (Exception ex) {
			return "ERROR";
		}
	}

	private String obtenerAmbiente() {
		String[] profiles = environment.getActiveProfiles();
		if (profiles.length == 0) {
			return "default";
		}
		return String.join(",", Arrays.asList(profiles));
	}

	private String obtenerPid() {
		return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}
}
