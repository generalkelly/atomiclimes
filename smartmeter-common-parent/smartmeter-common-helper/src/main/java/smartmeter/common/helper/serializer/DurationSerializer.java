package smartmeter.common.helper.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DurationSerializer extends StdDeserializer<Duration> {

	public DurationSerializer() {
		this(null);
	}

	public DurationSerializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 331860128536841286L;

	@Override
	public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Long durationInMinutes = p.getLongValue();
		return Duration.ofMinutes(durationInMinutes);
	}

}
