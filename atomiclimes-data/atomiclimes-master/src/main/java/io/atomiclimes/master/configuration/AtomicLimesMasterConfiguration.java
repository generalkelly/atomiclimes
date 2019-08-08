package io.atomiclimes.master.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.master.controller.AtomicLimesKafkaAdministration;

@Configuration
@EnableConfigurationProperties(value = AtomicLimesMasterProperties.class)
public class AtomicLimesMasterConfiguration {

	private AtomicLimesMasterProperties properties;

	AtomicLimesMasterConfiguration(AtomicLimesMasterProperties properties){
		this.properties = properties;
	}
	
	@Bean
	AtomicLimesAgentRegistry agentRegistry() {
		return new AtomicLimesAgentRegistry();
	}

	@Bean
	AtomicLimesAgentHealthMonitor healthMonitor(AtomicLimesAgentRegistry agentRegistry) {
		return new AtomicLimesAgentHealthMonitor(agentRegistry, this.properties);
	}

	@Bean
	AtomicLimesMasterStartupListener applicationStarted(AtomicLimesAgentHealthMonitor healthMonitor) {
		return new AtomicLimesMasterStartupListener(healthMonitor);
	}
	
	@Bean
	AtomicLimesKafkaAdministration kafkaAdministration() {
		return new AtomicLimesKafkaAdministration(this.properties.getKafkaProperties());
	}

}
