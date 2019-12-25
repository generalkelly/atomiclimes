package io.atomiclimes.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

@Configuration
public class ProxyConfig {

	@Bean
	public AtomicLimesClient client(AtomicLimesClientProperties properties) {
		AtomicLimesClient client = new AtomicLimesClient();
		client.setType(ClientType.PROXY);
		client.setPort(properties.getPort());
		return client;
	}

}
