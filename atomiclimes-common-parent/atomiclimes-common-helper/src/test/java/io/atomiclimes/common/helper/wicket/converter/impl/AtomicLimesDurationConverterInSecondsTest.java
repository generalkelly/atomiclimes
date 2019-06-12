package io.atomiclimes.common.helper.wicket.converter.impl;

import java.time.Duration;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AtomicLimesDurationConverterInSecondsTest {

	private AtomicLimesDurationInSecondsConverter atomicLimesDurationInSecondsConverter;
	private Locale locale = Locale.GERMANY;

	@Before
	public void setup() {
		this.atomicLimesDurationInSecondsConverter = new AtomicLimesDurationInSecondsConverter();
	}

	@Test
	public void testConvertToObject() {
		Duration actual = this.atomicLimesDurationInSecondsConverter.convertToObject("10", this.locale);
		Assert.assertEquals(10, actual.getSeconds());
	}

	@Test
	public void testConvertToString() {
		String actual = this.atomicLimesDurationInSecondsConverter.convertToString(Duration.ofSeconds(10), this.locale);
		Assert.assertEquals("10", actual);
	}

}
