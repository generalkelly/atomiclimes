package io.atomiclimes.agent.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.agent.connectors.AtomicLimesAgentS7Connector;
import io.atomiclimes.client.connectors.AtomicLimesClientConnector;
import io.atomiclimes.client.listeners.ClientConnectedToMasterListener;

@Configuration
@EnableConfigurationProperties(AtomicLimesAgentProperties.class)
@EnableAutoConfiguration
public class AtomicLimesAgentConfiguration {

	private AtomicLimesAgentProperties properties;

	AtomicLimesAgentConfiguration(AtomicLimesAgentProperties properties) {
		this.properties = properties;
	}

	@Bean
	ClientConnectedToMasterListener agentConnectedToMasterListener(AtomicLimesClientConnector connector) {
		return new ClientConnectedToMasterListener(connector);
	}
	
	@Bean
	AtomicLimesClientConnector s7Connector() {
		return new AtomicLimesAgentS7Connector(this.properties);
	}

}
