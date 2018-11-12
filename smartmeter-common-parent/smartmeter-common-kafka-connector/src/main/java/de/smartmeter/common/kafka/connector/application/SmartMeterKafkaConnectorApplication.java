package de.smartmeter.common.kafka.connector.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartMeterKafkaConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMeterKafkaConnectorApplication.class, args);
	}

}
