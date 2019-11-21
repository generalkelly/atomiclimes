package io.atomiclimes.common.dao.entities.json;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONDurationToLongConverter extends StdConverter<Duration, Long> {

	@Override
	public Long convert(Duration value) {
		return value.get(ChronoUnit.SECONDS);
	}

}
