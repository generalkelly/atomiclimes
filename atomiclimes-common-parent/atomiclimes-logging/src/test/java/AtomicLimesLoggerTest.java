import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.atomiclimes.common.logging.AtomicLimesLogger;

@RunWith(SpringRunner.class)
public class AtomicLimesLoggerTest {

	private AtomicLimesLogger atomicLimesLogger;

	@Before
	public void setup() {
		this.atomicLimesLogger = new AtomicLimesLogger(AtomicLimesLoggerTest.class);
	}

	@Test
	public void testDebugAtomicLimesLogMessage() {
		atomicLimesLogger.debug(AtomicLimesTestMessage.TEST);
	}

	@Test
	public void testDebugAtomicLimesLogMessageStringArray() {
		atomicLimesLogger.debug(AtomicLimesTestMessage.TEST, "one", "two", "three");
	}

	@Test
	public void testDebugAtomicLimesLogMessageThrowableStringArray() {
		atomicLimesLogger.debug(AtomicLimesTestMessage.TEST, new FileNotFoundException(), "one", "two", "three");
	}

	@Test
	public void testInfoAtomicLimesLogMessage() {
		atomicLimesLogger.info(AtomicLimesTestMessage.TEST);
	}

	@Test
	public void testInfoAtomicLimesLogMessageStringArray() {
		atomicLimesLogger.info(AtomicLimesTestMessage.TEST, "one", "two", "three");
	}

	@Test
	public void testInfoAtomicLimesLogMessageThrowableStringArray() {
		atomicLimesLogger.info(AtomicLimesTestMessage.TEST, new FileNotFoundException(), "one", "two", "three");
	}

	@Test
	public void testErrorAtomicLimesLogMessage() {
		atomicLimesLogger.error(AtomicLimesTestMessage.TEST);
	}

	@Test
	public void testErrorAtomicLimesLogMessageStringArray() {
		atomicLimesLogger.error(AtomicLimesTestMessage.TEST, "one", "two", "three");
	}

	@Test
	public void testErrorAtomicLimesLogMessageThrowableStringArray() {
		atomicLimesLogger.error(AtomicLimesTestMessage.TEST, new FileNotFoundException(), "one", "two", "three");
	}

	@Test
	public void testWarnAtomicLimesLogMessage() {
		atomicLimesLogger.warn(AtomicLimesTestMessage.TEST);
	}

	@Test
	public void testWarnAtomicLimesLogMessageStringArray() {
		atomicLimesLogger.warn(AtomicLimesTestMessage.TEST, "one", "two", "three");
	}

	@Test
	public void testWarnAtomicLimesLogMessageThrowableStringArray() {
		atomicLimesLogger.warn(AtomicLimesTestMessage.TEST, new FileNotFoundException(), "one", "two", "three");
	}

}
