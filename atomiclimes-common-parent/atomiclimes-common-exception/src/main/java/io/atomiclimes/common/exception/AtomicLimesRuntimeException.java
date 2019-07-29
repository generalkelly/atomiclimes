package io.atomiclimes.common.exception;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public class AtomicLimesRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AtomicLimesRuntimeException() {
	}

	public AtomicLimesRuntimeException(AtomicLimesLogMessage message) {
		super(message.getMessage());
	}

	public AtomicLimesRuntimeException(AtomicLimesLogMessage message, Throwable throwable) {
		super(message.getMessage(), throwable);
	}

}
