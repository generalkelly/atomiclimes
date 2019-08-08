package io.atomiclimes.agent.logging;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesAgentLogMessage implements AtomicLimesLogMessage {
	FAILED_TO_RETRIEVE_HOST_ADDRESS_LOG_MESSAGE("Failed to retrieve host address"),
	FAILED_TO_CLOSE_SOCKET_LOG_MESSAGE("Failed to close socket."),
	SENDING_HEARTBEAT_LOG_MESSAGE("Sending heartbeat for agent {} with a heartbeat waiting time of {} seconds.");

	private String message;

	AtomicLimesAgentLogMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
