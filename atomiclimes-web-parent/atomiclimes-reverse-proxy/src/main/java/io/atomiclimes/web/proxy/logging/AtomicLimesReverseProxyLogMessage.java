package io.atomiclimes.web.proxy.logging;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesReverseProxyLogMessage implements AtomicLimesLogMessage {
	ADDED_ROUTE_TO_REVERSE_PROXY("Added route \"{}\" to reverse proxy."),
	REMOVED_PATH_FROM_REVERSE_PROXY("Removed path \"{}\" from reverse proxy.");

	private String message;

	AtomicLimesReverseProxyLogMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
