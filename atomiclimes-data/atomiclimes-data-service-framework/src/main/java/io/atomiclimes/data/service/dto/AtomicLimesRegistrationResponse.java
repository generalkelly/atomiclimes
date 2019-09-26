package io.atomiclimes.data.service.dto;

import java.util.HashMap;
import java.util.Map;

public class AtomicLimesRegistrationResponse {

	private int heartbeatWaitingTime = 10;

	private Map<String, Object> kafkaConfiguration = new HashMap<>();

	private Map<String, Object> clientConfiguration = new HashMap<>();

	public int getHeartbeatWaitingTime() {
		return heartbeatWaitingTime;
	}

	public void setHeartbeatWaitingTime(int heartbeatWaitingTime) {
		this.heartbeatWaitingTime = heartbeatWaitingTime;
	}

	public Map<String, Object> getKafkaConfiguration() {
		return kafkaConfiguration;
	}

	public void setKafkaConfiguration(Map<String, Object> kafkaConfiguration) {
		this.kafkaConfiguration = kafkaConfiguration;
	}

	public Map<String, Object> getClientConfiguration() {
		return clientConfiguration;
	}

	public void setClientConfiguration(Map<String, Object> clientConfiguration) {
		this.clientConfiguration = clientConfiguration;
	}

}
