package io.atomiclimes.common.dao.entities.json;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONOffsetDateTimeToStringConverter extends StdConverter<OffsetDateTime, String> {

	@Override
	public String convert(OffsetDateTime value) {
		return value.toString();
	}

}
