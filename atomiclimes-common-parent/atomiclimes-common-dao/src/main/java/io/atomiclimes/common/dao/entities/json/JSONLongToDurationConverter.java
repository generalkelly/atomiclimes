package io.atomiclimes.common.dao.entities.json;

import java.time.Duration;

import com.fasterxml.jackson.databind.util.StdConverter;

public class JSONLongToDurationConverter extends StdConverter<Long, Duration> {

	@Override
	public Duration convert(Long value) {
		return Duration.ofSeconds(value);
	}

}
