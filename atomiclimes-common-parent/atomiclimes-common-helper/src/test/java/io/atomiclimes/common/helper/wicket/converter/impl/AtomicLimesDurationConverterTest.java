package io.atomiclimes.common.helper.wicket.converter.impl;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AtomicLimesDurationConverterTest {

	private AtomicLimesDurationConverter atomicLimesDurationConverter;
	private Locale locale = Locale.GERMANY;

	@Before
	public void setup() {
		this.atomicLimesDurationConverter = new AtomicLimesDurationConverter(ChronoUnit.SECONDS);
	}

	@Test
	public void testConvertToObject() {
		Duration actual = this.atomicLimesDurationConverter.convertToObject("10", this.locale);
		Assert.assertEquals(10, actual.getSeconds());
	}

	@Test
	public void testConvertToString() {
		String actual = this.atomicLimesDurationConverter.convertToString(Duration.ofSeconds(10), this.locale);
		Assert.assertEquals("10", actual);
	}

}
