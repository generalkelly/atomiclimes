package io.atomiclimes.client.logging;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesClientLogMessage implements AtomicLimesLogMessage {
	FAILED_TO_RETRIEVE_HOST_ADDRESS_LOG_MESSAGE("Failed to retrieve host address"),
	FAILED_TO_CLOSE_SOCKET_LOG_MESSAGE("Failed to close socket."),
	SENDING_HEARTBEAT_LOG_MESSAGE("Sending heartbeat for agent {} with a heartbeat waiting time of {} seconds."),
	REGISTERED_CLIENT_LOG_MESSAGE("Registered client {} at Master."),
	REGISTERING_CLIENT_FAILED_LOG_MESSAGE("Registration of client {} failed.");

	private String message;

	AtomicLimesClientLogMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
