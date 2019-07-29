package io.atomiclimes.communication;

public class AtomicLimesRegistrationResponse {

	private int heartbeatWaitingTime = 10;

	private KafkaConfiguration kafkaConfiguration;

	public int getHeartbeatWaitingTime() {
		return heartbeatWaitingTime;
	}

	public void setHeartbeatWaitingTime(int heartbeatWaitingTime) {
		this.heartbeatWaitingTime = heartbeatWaitingTime;
	}

	public KafkaConfiguration getKafkaConfiguration() {
		return kafkaConfiguration;
	}

	public void setKafkaConfiguration(KafkaConfiguration kafkaConfiguration) {
		this.kafkaConfiguration = kafkaConfiguration;
	}

}
