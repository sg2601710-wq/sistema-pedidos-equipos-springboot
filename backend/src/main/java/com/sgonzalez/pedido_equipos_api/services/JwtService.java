package com.sgonzalez.pedido_equipos_api.services;

import com.sgonzalez.pedido_equipos_api.models.Usuario;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private static final String ALGORITHM = "HmacSHA256";

	private final String secret;
	private final long expirationMinutes;

	public JwtService(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration-minutes}") long expirationMinutes
	) {
		this.secret = secret;
		this.expirationMinutes = expirationMinutes;
	}

	public String generarToken(Usuario usuario) {
		Instant now = Instant.now();

		Map<String, Object> header = Map.of(
				"alg", "HS256",
				"typ", "JWT"
		);

		Map<String, Object> payload = new LinkedHashMap<>();
		payload.put("sub", usuario.getEmail());
		payload.put("usuarioId", usuario.getId());
		payload.put("nombre", usuario.getNombre());
		payload.put("rol", usuario.getRol().getNombre());
		payload.put("iat", now.getEpochSecond());
		payload.put("exp", now.plus(expirationMinutes, ChronoUnit.MINUTES).getEpochSecond());

		String encodedHeader = encodeJson(header);
		String encodedPayload = encodeJson(payload);
		String signature = firmar(encodedHeader + "." + encodedPayload);

		return encodedHeader + "." + encodedPayload + "." + signature;
	}

	public boolean esTokenValido(String token) {
		try {
			Map<String, Object> claims = obtenerClaims(token);
			Object expiration = claims.get("exp");

			if (!(expiration instanceof Number)) {
				return false;
			}

			return ((Number) expiration).longValue() > Instant.now().getEpochSecond();
		} catch (IllegalArgumentException exception) {
			return false;
		}
	}

	public Map<String, Object> obtenerClaims(String token) {
		String[] parts = token == null ? new String[0] : token.split("\\.");

		if (parts.length != 3) {
			throw new IllegalArgumentException("Token invalido");
		}

		String unsignedToken = parts[0] + "." + parts[1];
		String expectedSignature = firmar(unsignedToken);

		if (!MessageDigest.isEqual(
				expectedSignature.getBytes(StandardCharsets.UTF_8),
				parts[2].getBytes(StandardCharsets.UTF_8)
		)) {
			throw new IllegalArgumentException("Token invalido");
		}

		try {
			String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
			return parseClaims(payload);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException("Token invalido", exception);
		}
	}

	private String encodeJson(Map<String, Object> value) {
		return Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(toJson(value).getBytes(StandardCharsets.UTF_8));
	}

	private String firmar(String data) {
		try {
			Mac mac = Mac.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
			mac.init(keySpec);

			return Base64.getUrlEncoder()
					.withoutPadding()
					.encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception exception) {
			throw new IllegalStateException("No se pudo firmar el token", exception);
		}
	}

	private String toJson(Map<String, Object> value) {
		StringBuilder json = new StringBuilder("{");
		boolean first = true;

		for (Map.Entry<String, Object> entry : value.entrySet()) {
			if (!first) {
				json.append(",");
			}

			json.append("\"").append(escapeJson(entry.getKey())).append("\":");

			if (entry.getValue() instanceof Number || entry.getValue() instanceof Boolean) {
				json.append(entry.getValue());
			} else {
				json.append("\"").append(escapeJson(String.valueOf(entry.getValue()))).append("\"");
			}

			first = false;
		}

		return json.append("}").toString();
	}

	private Map<String, Object> parseClaims(String json) {
		Map<String, Object> claims = new LinkedHashMap<>();

		putIfPresent(claims, "sub", extractString(json, "sub"));
		putIfPresent(claims, "usuarioId", extractLong(json, "usuarioId"));
		putIfPresent(claims, "nombre", extractString(json, "nombre"));
		putIfPresent(claims, "rol", extractString(json, "rol"));
		putIfPresent(claims, "iat", extractLong(json, "iat"));
		putIfPresent(claims, "exp", extractLong(json, "exp"));

		return claims;
	}

	private void putIfPresent(Map<String, Object> claims, String key, Object value) {
		if (value != null) {
			claims.put(key, value);
		}
	}

	private String extractString(String json, String key) {
		Matcher matcher = Pattern.compile("\"" + key + "\"\\s*:\\s*\"((?:\\\\.|[^\"])*)\"").matcher(json);
		return matcher.find() ? unescapeJson(matcher.group(1)) : null;
	}

	private Long extractLong(String json, String key) {
		Matcher matcher = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)").matcher(json);
		return matcher.find() ? Long.valueOf(matcher.group(1)) : null;
	}

	private String escapeJson(String value) {
		return value
				.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\n", "\\n")
				.replace("\r", "\\r")
				.replace("\t", "\\t");
	}

	private String unescapeJson(String value) {
		return value
				.replace("\\t", "\t")
				.replace("\\r", "\r")
				.replace("\\n", "\n")
				.replace("\\\"", "\"")
				.replace("\\\\", "\\");
	}
}
