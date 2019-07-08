package io.atomiclimes.common.logging;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mirko Pohland
 *
 */

public class AtomicLimesLogger {

	private Logger logger;

	/**
	 * @param clazz the class from which the logger is called
	 */
	public AtomicLimesLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	/**
	 * This method will only log if the system is in debug mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 */
	public void debug(AtomicLimesLogMessage message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message.getMessage());
		}
	}

	/**
	 * This method will only log if the system is in debug mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 * @param args    Array of Strings which replace occurrences of the regex {}
	 */
	public void debug(AtomicLimesLogMessage message, String... args) {
		if (logger.isDebugEnabled()) {
			logger.debug(Substitutor.substitute(message, args));
		}
	}

	/**
	 * This method will only log if the system is in debug mode
	 * 
	 * @param message   instance of {@link AtomicLimesLogMessage} the message which
	 *                  should be logged
	 * @param throwable instance of {@link Throwable}
	 * @param args      Array of Strings which replace occurrences of the regex {}
	 */
	public void debug(AtomicLimesLogMessage message, Throwable throwable, String... args) {
		if (logger.isDebugEnabled()) {
			logger.debug(Substitutor.substitute(message, args), throwable);
		}
	}

	/**
	 * This method will only log if the system is in info mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 */
	public void info(AtomicLimesLogMessage message) {
		if (logger.isInfoEnabled()) {
			logger.info(message.getMessage());
		}
	}

	/**
	 * This method will only log if the system is in info mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 * @param args    Array of Strings which replace occurrences of the regex {}
	 */
	public void info(AtomicLimesLogMessage message, String... args) {
		if (logger.isInfoEnabled()) {
			logger.info(Substitutor.substitute(message, args));
		}
	}

	/**
	 * This method will only log if the system is in info mode
	 * 
	 * @param message   instance of {@link AtomicLimesLogMessage} the message which
	 *                  should be logged
	 * @param throwable instance of {@link Throwable}
	 * @param args      Array of Strings which replace occurrences of the regex {}
	 */
	public void info(AtomicLimesLogMessage message, Throwable throwable, String... args) {
		if (logger.isInfoEnabled()) {
			logger.info(Substitutor.substitute(message, args), throwable);
		}
	}

	/**
	 * This method will only log if the system is in error mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 */
	public void error(AtomicLimesLogMessage message) {
		if (logger.isErrorEnabled()) {
			logger.error(message.getMessage());
		}
	}

	/**
	 * This method will only log if the system is in error mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 * @param args    Array of Strings which replace occurrences of the regex {}
	 */
	public void error(AtomicLimesLogMessage message, String... args) {
		if (logger.isErrorEnabled()) {
			logger.error(Substitutor.substitute(message, args));
		}
	}

	/**
	 * This method will only log if the system is in error mode
	 * 
	 * @param message   instance of {@link AtomicLimesLogMessage} the message which
	 *                  should be logged
	 * @param throwable instance of {@link Throwable}
	 * @param args      Array of Strings which replace occurrences of the regex {}
	 */
	public void error(AtomicLimesLogMessage message, Throwable throwable, String... args) {
		if (logger.isErrorEnabled()) {
			logger.error(Substitutor.substitute(message, args), throwable);
		}
	}

	/**
	 * This method will only log if the system is in warn mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 */
	public void warn(AtomicLimesLogMessage message) {
		if (logger.isWarnEnabled()) {
			logger.warn(message.getMessage());
		}
	}

	/**
	 * This method will only log if the system is in warn mode
	 * 
	 * @param message instance of {@link AtomicLimesLogMessage} the message which
	 *                should be logged
	 * @param args    Array of Strings which replace occurrences of the regex {}
	 */
	public void warn(AtomicLimesLogMessage message, String... args) {
		if (logger.isWarnEnabled()) {
			logger.warn(Substitutor.substitute(message, args));
		}
	}

	/**
	 * This method will only log if the system is in warn mode
	 * 
	 * @param message   instance of {@link AtomicLimesLogMessage} the message which
	 *                  should be logged
	 * @param throwable instance of {@link Throwable}
	 * @param args      Array of Strings which replace occurrences of the regex {}
	 */
	public void warn(AtomicLimesLogMessage message, Throwable throwable, String... args) {
		if (logger.isWarnEnabled()) {
			logger.warn(Substitutor.substitute(message, args), throwable);
		}
	}

	private static class Substitutor {

		private static final String REPLACE_PATTERN = "\\{\\}";

		static final Pattern PATTERN = Pattern.compile(REPLACE_PATTERN);

		/**
		 * @param substitutionCandidate instance of {@link AtomicLimesLogMessage}
		 * @param substitutors          an array of strings which will be substituted
		 *                              into the substitutionCandidate
		 * @return The substitutionCandidate with all occurrences of {} replaced with
		 *         the available substitutors
		 */
		public static String substitute(AtomicLimesLogMessage substitutionCandidate, String... substitutors) {

			List<String> substitutorsList = new LinkedList<>();
			CollectionUtils.addAll(substitutorsList, substitutors);
			Iterator<String> iterator = substitutorsList.iterator();

			String message = substitutionCandidate.getMessage();

			while (iterator.hasNext()) {
				Matcher matcher = PATTERN.matcher(message);
				message = matcher.replaceFirst(iterator.next());
			}
			return message;
		}

	}

}