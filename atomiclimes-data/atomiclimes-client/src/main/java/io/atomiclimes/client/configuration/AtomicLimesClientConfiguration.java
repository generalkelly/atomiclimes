package io.atomiclimes.client.configuration;

import java.util.Set;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.atomiclimes.client.controller.AtomicLimesClientController;
import io.atomiclimes.client.notification.filter.AtomicLimesClientNotificationFilterRegistry;
import io.atomiclimes.client.notification.filter.ClientNotificationFilter;
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
	AtomicLimesClientStartupListener applicationStartupListener(ApplicationEventPublisher applicationEventPublisher,
			AtomicLimesClient client) {
		return new AtomicLimesClientStartupListener(applicationEventPublisher, this.properties, client);
	}

	@Bean
	AtomicLimesClientController atomicLimesClientController(
			AtomicLimesClientNotificationFilterRegistry atomicLimesClientNotificationFilterRegistry) {
		return new AtomicLimesClientController(this.properties, atomicLimesClientNotificationFilterRegistry);
	}

	@Bean
	AtomicLimesClientNotificationFilterRegistry atomicLimesClientNotificationFilterRegistry(
			Set<ClientNotificationFilter> clientNotificationFilters) {
		return new AtomicLimesClientNotificationFilterRegistry(clientNotificationFilters);
	}

}
