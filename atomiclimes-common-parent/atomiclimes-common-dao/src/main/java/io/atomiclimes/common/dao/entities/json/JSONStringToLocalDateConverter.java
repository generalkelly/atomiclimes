package io.atomiclimes.common.dao.entities.json;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONStringToLocalDateConverter extends StdConverter<String, LocalDate> {

	@Override
	public LocalDate convert(String value) {
		return LocalDate.parse(value);
	}

}
