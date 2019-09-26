package io.atomiclimes.data.service.master.filters;

import java.util.HashMap;
import java.util.Map;

import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.atomiclimes.data.service.master.configuration.AtomicLimesClientRegistry;
import io.atomiclimes.data.service.master.configuration.AtomicLimesMasterProperties;
import io.atomiclimes.date.service.client.enums.ClientType;

public class DefaultClientFilter implements AtomicLimesClientFilter {

	private AtomicLimesClientRegistry registry;
	private AtomicLimesMasterProperties properties;

	public DefaultClientFilter(AtomicLimesClientRegistry registry, AtomicLimesMasterProperties properties) {
		this.registry = registry;
		this.properties = properties;
	}

	@Override
	public AtomicLimesRegistrationResponse filter(AtomicLimesClient client) {
		this.registry.register(client);
		AtomicLimesRegistrationResponse registrationResponse = new AtomicLimesRegistrationResponse();
		registrationResponse.setHeartbeatWaitingTime(properties.getHeartbeatWaitingTime());
		Map<String, Object> clientConfiguration = new HashMap<>();
//			Right now the clientConfiguration Map is empty, this might change later
		registrationResponse.setClientConfiguration(clientConfiguration);
		return registrationResponse;
	}

	@Override
	public ClientType getType() {
		return ClientType.DEFAULT;
	}

}
