package io.atomiclimes.common.helper.wicket.converter.impl;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class AtomicLimesDurationInSecondsConverter extends AtomicLimesConverter<Duration> {

	private static final long serialVersionUID = 1L;
	private AtomicLimesDurationConverter atomicLimesDurationConverter;

	public AtomicLimesDurationInSecondsConverter() {
		this.atomicLimesDurationConverter = new AtomicLimesDurationConverter(ChronoUnit.SECONDS);
	}

	@Override
	public Duration convertToObject(String value, Locale locale) {
		return atomicLimesDurationConverter.convertToObject(value, locale);
	}

	@Override
	public String convertToString(Duration value, Locale locale) {
		return atomicLimesDurationConverter.convertToString(value, locale);
	}

}
