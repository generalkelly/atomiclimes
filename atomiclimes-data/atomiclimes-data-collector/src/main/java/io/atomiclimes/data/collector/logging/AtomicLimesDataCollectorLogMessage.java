package io.atomiclimes.data.collector.logging;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesDataCollectorLogMessage implements AtomicLimesLogMessage {
	TRANSPORTER_ERROR_LOG_MESSAGE("An error occured in a transporter thread.");

	private String message;

	private AtomicLimesDataCollectorLogMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
