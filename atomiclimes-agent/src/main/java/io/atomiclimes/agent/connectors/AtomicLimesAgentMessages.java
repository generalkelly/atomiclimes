package io.atomiclimes.agent.connectors;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesAgentMessages implements AtomicLimesLogMessage {

	COULD_NOT_CONNECT("Could not connect to S7 with ip={}, rack={} and slot={}. Retry..."),
	KAFKA_CONFIGURATION_IS_NULL_LOG_MESSAGE("Kafka configuration is null."),
	CONNECTED_TO_PLC_LOG_MESSAGE("Successfully connected to plc with connection string: {}."),
	PLC_EXCEPTION_LOG_MESSAGE("An error happened during connection to Plc.");

	private String message;

	AtomicLimesAgentMessages(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
