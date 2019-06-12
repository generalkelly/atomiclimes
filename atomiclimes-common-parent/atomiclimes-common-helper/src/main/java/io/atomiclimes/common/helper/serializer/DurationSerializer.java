package io.atomiclimes.common.helper.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DurationSerializer extends StdDeserializer<Duration> {

	private static final long serialVersionUID = 1L;

	public DurationSerializer() {
		this(null);
	}

	public DurationSerializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		Long durationInMinutes = p.getLongValue();
		return Duration.ofMinutes(durationInMinutes);
	}

}
