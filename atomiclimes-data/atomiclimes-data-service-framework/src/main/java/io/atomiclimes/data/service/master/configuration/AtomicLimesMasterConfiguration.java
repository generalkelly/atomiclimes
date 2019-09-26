package io.atomiclimes.data.service.master.configuration;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.data.service.master.controller.AtomicLimesKafkaAdministration;
import io.atomiclimes.data.service.master.controller.AtomicLimesMasterController;
import io.atomiclimes.data.service.master.filters.AtomicLimesClientFilter;
import io.atomiclimes.data.service.master.filters.DefaultClientFilter;
import io.atomiclimes.data.service.master.filters.FilterEntry;
import io.atomiclimes.date.service.client.enums.ClientType;

@Configuration
@EnableConfigurationProperties(value = AtomicLimesMasterProperties.class)
public class AtomicLimesMasterConfiguration {

	private AtomicLimesMasterProperties properties;

	AtomicLimesMasterConfiguration(AtomicLimesMasterProperties properties) {
		this.properties = properties;
	}

	@Bean
	AtomicLimesClientCollection atomicLimesClientCollection(Set<FilterEntry> clientFilters) {
		return new AtomicLimesClientCollection(clientFilters);
	}

	@Bean
	FilterEntry defaultClientFilterEntry(AtomicLimesClientRegistry agentRegistry) {
		return new FilterEntry(new DefaultClientFilter(agentRegistry, this.properties));
	}

	@Bean
	AtomicLimesClientRegistry agentRegistry() {
		return new AtomicLimesClientRegistry();
	}

	@Bean
	AtomicLimesAgentHealthMonitor healthMonitor(AtomicLimesClientRegistry agentRegistry) {
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

	@Bean
	AtomicLimesMasterController controller() {
		return new AtomicLimesMasterController();
	}

}
