package com.sgonzalez.pedido_equipos_api.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, String> {

	@Override
	public String convertToDatabaseColumn(LocalDateTime value) {
		return value == null ? null : value.toString();
	}

	@Override
	public LocalDateTime convertToEntityAttribute(String value) {
		return value == null ? null : LocalDateTime.parse(value.replace(" ", "T"));
	}
}
