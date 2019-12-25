package io.atomiclimes.master.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.data.service.master.configuration.AtomicLimesClientRegistry;
import io.atomiclimes.data.service.master.configuration.AtomicLimesMasterProperties;
import io.atomiclimes.data.service.master.controller.AtomicLimesKafkaAdministration;
import io.atomiclimes.data.service.master.filters.FilterEntry;
import io.atomiclimes.master.filters.AgentClientFilter;
import io.atomiclimes.master.filters.GuiClientFilter;
import io.atomiclimes.master.filters.ProxyClientFilter;

@Configuration
public class AtomicLimesMasterKafkaConfiguration {

	@Bean
	FilterEntry agentClientFilterEntry(AtomicLimesClientRegistry clientRegistry, AtomicLimesMasterProperties properties,
			AtomicLimesKafkaAdministration kafkaAdministration) {
		return new FilterEntry(new AgentClientFilter(clientRegistry, properties, kafkaAdministration));
	}

	@Bean
	FilterEntry proxyClientFilterEntry(AtomicLimesClientRegistry clientRegistry, AtomicLimesMasterProperties properties) {
		return new FilterEntry(new ProxyClientFilter(clientRegistry, properties));
	}

	@Bean
	FilterEntry guiClientFilterEntry(AtomicLimesClientRegistry clientRegistry, AtomicLimesMasterProperties properties) {
		return new FilterEntry(new GuiClientFilter(clientRegistry, properties));
	}

}
