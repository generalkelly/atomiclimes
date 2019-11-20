package io.atomiclimes.common.dao.entities.json;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONDurationToStringConverter extends StdConverter<Duration, String> {

	@Override
	public String convert(Duration value) {
		return Long.toString(value.get(ChronoUnit.SECONDS));
	}

}
