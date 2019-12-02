package io.atomiclimes.common.dao.entities.json;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONStringToOffsetDateTimeConverter extends StdConverter<String, OffsetDateTime> {

	@Override
	public OffsetDateTime convert(String value) {
		return OffsetDateTime.parse(value);
	}

}
