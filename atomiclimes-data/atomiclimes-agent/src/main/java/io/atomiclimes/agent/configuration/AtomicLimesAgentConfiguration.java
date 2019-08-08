package io.atomiclimes.agent.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.agent.connectors.AtomicLimesAgentConnector;
import io.atomiclimes.agent.connectors.AtomicLimesAgentS7Connector;

@Configuration
@EnableConfigurationProperties(AtomicLimesAgentProperties.class)
@EnableAutoConfiguration
public class AtomicLimesAgentConfiguration {

	private AtomicLimesAgentProperties properties;

	AtomicLimesAgentConfiguration(AtomicLimesAgentProperties properties) {
		this.properties = properties;
	}

	@Bean
	AtomicLimesAgentConnector s7Connector() {
		return new AtomicLimesAgentS7Connector(this.properties);
	}


	@Bean
	StartupListener applicationStartupListener(ApplicationEventPublisher applicationEventPublisher) {
		return new StartupListener(applicationEventPublisher, this.properties);
	}

	@Bean
	AgentConnectedToMasterListener agentConnectedToMasterListener(AtomicLimesAgentConnector connector) {
		return new AgentConnectedToMasterListener(connector);
	}

}
