package io.atomiclimes.data.collector.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.data.collector.listener.AtomicLimesaDataCollectorStartupListener;

@Configuration
@EnableConfigurationProperties(AtomicLimesDataCollectorProperties.class)
@EnableAutoConfiguration
public class AtomicLimesDataCollectorConfiguration {

	private AtomicLimesDataCollectorProperties properties;


	public AtomicLimesDataCollectorConfiguration(AtomicLimesDataCollectorProperties properties) {
		this.properties = properties;
	}

	@Bean
	AtomicLimesaDataCollectorStartupListener startupListener(AtomicLimesaDataCollectorAgentRegistry agentRegistry) {
		return new AtomicLimesaDataCollectorStartupListener(agentRegistry);
	}

	@Bean
	AtomicLimesaDataCollectorAgentRegistry agentRegistry(ApplicationEventPublisher applicationEventPublisher) {
		return new AtomicLimesaDataCollectorAgentRegistry(this.properties, applicationEventPublisher);
	}
	
	@Bean
	AtomicLimesDataTransporterRegistry transporterRegistry() {
		return new AtomicLimesDataTransporterRegistry(this.properties);
	}
	
	@Bean
	NewAgentsEventListener newAgentsEventListener(AtomicLimesDataTransporterRegistry transporterRegistry) {
		return new NewAgentsEventListener(transporterRegistry);
	}

}
