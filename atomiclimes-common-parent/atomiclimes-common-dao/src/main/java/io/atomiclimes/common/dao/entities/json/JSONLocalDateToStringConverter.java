package io.atomiclimes.common.dao.entities.json;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONLocalDateToStringConverter extends StdConverter<LocalDate, String> {

	@Override
	public String convert(LocalDate value) {
		return value.toString();
	}

}
