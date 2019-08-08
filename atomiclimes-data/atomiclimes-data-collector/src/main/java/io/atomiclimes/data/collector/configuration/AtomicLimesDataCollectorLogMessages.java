package io.atomiclimes.data.collector.configuration;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesDataCollectorLogMessages implements AtomicLimesLogMessage {
	CREATED_TRANSPORTER_LOG_MESSAGE("Created data collector for queue {}")

	;
	private String message;

	private AtomicLimesDataCollectorLogMessages(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
