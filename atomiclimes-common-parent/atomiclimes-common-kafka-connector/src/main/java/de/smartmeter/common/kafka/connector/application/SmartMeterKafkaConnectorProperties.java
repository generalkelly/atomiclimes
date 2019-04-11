package de.smartmeter.common.kafka.connector.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

import de.smartmeter.common.kafka.connector.util.KafkaProperties;
import de.smartmeter.common.kafka.connector.util.S7Properties;

@ConfigurationProperties(prefix = "de.smartmeter.common.kafka.connector")
public class SmartMeterKafkaConnectorProperties {

	private S7Properties s7Properties;
	private KafkaProperties KafkaProperties;

	public S7Properties getS7Properties() {
		return s7Properties;
	}

	public void setS7Properties(S7Properties s7Properties) {
		this.s7Properties = s7Properties;
	}

	public KafkaProperties getKafkaProperties() {
		return KafkaProperties;
	}

	public void setKafkaProperties(KafkaProperties kafkaProperties) {
		KafkaProperties = kafkaProperties;
	}

}
