package io.atomiclimes.common.dao.entities.json;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONStringToDurationConverter extends StdConverter<String, Duration> {

	@Override
	public Duration convert(String value) {
		return Duration.ofSeconds(Long.parseLong(value));
	}

}
