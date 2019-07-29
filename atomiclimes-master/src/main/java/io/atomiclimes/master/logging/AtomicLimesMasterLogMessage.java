package io.atomiclimes.master.logging;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesMasterLogMessage implements AtomicLimesLogMessage {
	STARTED_AGENT_HEALTH_MONITOR("Started agent health monitor."),
	RECEIVED_HARTBEAT_LOG_MESSAGE("Received heartbeat from agent: {}"),
	AGENT_REGISTERED_LOG_MESSAGE("Agent {} registered at master."),
	AGENT_DOWN_LOG_MESSAGE("Agent {} did not send a heartbeat within the configured timeframe of {} seconds."),
	KAFKA_ADMINISTRATION_FAILURE_LOG_MESSAGE("Failed to create topic for agent {}."),
	KAFKA_CONFIGURATION_ERROR_LOG_MESSAGE("An error occured during creation of the kafka configuration."),
	COULD_NOT_RETRIEVE_TOPICS_LOG_MESSAGE("Could not retrieve list of existing topics"),
	KAFKA_TOPIC_ALLREADY_EXISTS_FOR_AGENT_LOG_MESSAGE("Kafka Topic with name {} allready exists for agent {}.");

	private String message;

	AtomicLimesMasterLogMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
