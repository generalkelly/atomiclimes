package io.atomiclimes.master.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.data.service.master.configuration.AtomicLimesClientRegistry;
import io.atomiclimes.data.service.master.configuration.AtomicLimesMasterProperties;
import io.atomiclimes.data.service.master.controller.AtomicLimesKafkaAdministration;
import io.atomiclimes.data.service.master.filters.FilterEntry;
import io.atomiclimes.master.filters.AgentClientFilter;

@Configuration
public class AtomicLimesMasterKafkaConfiguration {

	@Bean
	FilterEntry agentClientFilterEntry(AtomicLimesClientRegistry agentRegistry,
			AtomicLimesMasterProperties properties, AtomicLimesKafkaAdministration kafkaAdministration) {
		return new FilterEntry(new AgentClientFilter(agentRegistry, properties, kafkaAdministration));
	}

}
