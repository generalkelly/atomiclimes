package io.atomiclimes.common.dao.entities.converters;

import java.util.UUID;

import javax.persistence.AttributeConverter;

public class UUIDConverter implements AttributeConverter<UUID, String> {

	@Override
	public String convertToDatabaseColumn(UUID attribute) {
		return attribute.toString();
	}

	@Override
	public UUID convertToEntityAttribute(String dbData) {
		return UUID.fromString(dbData);
	}

}
