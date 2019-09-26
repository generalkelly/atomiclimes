package io.atomiclimes.client.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import io.atomiclimes.client.connectors.AtomicLimesClientConnector;
import io.atomiclimes.client.controller.AtomicLimesClientController;
import io.atomiclimes.client.listeners.ClientConnectedToMasterListener;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.client.listeners.AtomicLimesClientStartupListener;

@Configuration
@EnableConfigurationProperties(AtomicLimesClientProperties.class)
@EnableAutoConfiguration
public class AtomicLimesClientConfiguration {

	private AtomicLimesClientProperties properties;

	AtomicLimesClientConfiguration(AtomicLimesClientProperties properties) {
		this.properties = properties;
	}

	@Bean
	AtomicLimesClientStartupListener applicationStartupListener(ApplicationEventPublisher applicationEventPublisher, AtomicLimesClient client) {
		return new AtomicLimesClientStartupListener(applicationEventPublisher, this.properties, client);
	}

	@Bean
	AtomicLimesClientController atomicLimesClientController() {
		return new AtomicLimesClientController(this.properties);
	}

}
