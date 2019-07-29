package smartmeter.common.helper.jackson;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import io.atomiclimes.helper.jackson.AtomicLimesJacksonHelper;

public class SmartmeterJacksonHelperTest {

	private static final String JSON_STRING = "{\"firstString\":\"firstString\",\"secondString\":\"secondString\"}";
	private AtomicLimesJacksonHelper smartmeterJacksonHelper;

	@Before
	public void setup() {
		this.smartmeterJacksonHelper = new AtomicLimesJacksonHelper(TestClass.class);

	}

	@Test
	public void serializeTest() {
		String jsonString = smartmeterJacksonHelper.serialize(new TestClass());
		assertEquals(JSON_STRING, jsonString);
	}

	@Test
	public void deserializeTest() {
		TestClass testClass = (TestClass) smartmeterJacksonHelper.deserialize(JSON_STRING);
		assertEquals("firstString", testClass.getFirstString());
	}

	public static class TestClass {
		private String firstString = "firstString";
		private String secondString = "secondString";

		public String getFirstString() {
			return firstString;
		}

		public void setFirstString(String firstString) {
			this.firstString = firstString;
		}

		public String getSecondString() {
			return secondString;
		}

		public void setSecondString(String secondString) {
			this.secondString = secondString;
		}

	}

}
