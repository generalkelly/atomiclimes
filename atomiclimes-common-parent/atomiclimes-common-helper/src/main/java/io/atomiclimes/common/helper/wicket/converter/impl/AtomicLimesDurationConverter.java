package io.atomiclimes.common.helper.wicket.converter.impl;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.wicket.util.convert.ConversionException;

import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class AtomicLimesDurationConverter extends AtomicLimesConverter<Duration> {

	private static final long serialVersionUID = 1L;
	private static final Pattern LONG_REGEX = Pattern.compile("^-?(0|([1-9])\\d{0,18})$");
	private TemporalUnit temporalUnit;

	public AtomicLimesDurationConverter(TemporalUnit temporalUnit) {
		this.temporalUnit = temporalUnit;
	}

	@Override
	public Duration convertToObject(String value, Locale locale) {
		if (!LONG_REGEX.matcher(value).matches()) {
			throw new ConversionException("String does not represent a Long value");
		} else {
			return Duration.of(Long.parseLong(value), this.temporalUnit);
		}
	}

	@Override
	public String convertToString(Duration value, Locale locale) {
		return Long.toString(value.get(this.temporalUnit));
	}

	
	
}
