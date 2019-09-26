package io.atomiclimes.data.service.master.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.atomiclimes.master")
public class AtomicLimesMasterProperties {

	private int heartbeatWaitingTime = 10;

	private AtomicLimesKafkaProperties kafkaProperties = new AtomicLimesKafkaProperties();

	public int getHeartbeatWaitingTime() {
		return heartbeatWaitingTime;
	}

	public void setHeartbeatWaitingTime(int heartbeatWaitingTime) {
		this.heartbeatWaitingTime = heartbeatWaitingTime;
	}

	public AtomicLimesKafkaProperties getKafkaProperties() {
		return kafkaProperties;
	}

	public void setKafkaProperties(AtomicLimesKafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

}
