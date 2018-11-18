package de.smartmeter.common.kafka.connector.application;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.smartmeter.common.kafka.connector.S7KafkaConnector;

@Configuration
@EnableConfigurationProperties(SmartMeterKafkaConnectorProperties.class)
public class SmartMeterKafkaConnectorConfiguration {

	private SmartMeterKafkaConnectorProperties properties;

	public SmartMeterKafkaConnectorConfiguration(SmartMeterKafkaConnectorProperties properties) {
		this.properties = properties;
	}

	@Bean
	public S7KafkaConnector s7KafkaConnector() {
		return new S7KafkaConnector(this.properties);
	}

}
