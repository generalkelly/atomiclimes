package io.atomiclimes.master.filters;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.atomiclimes.data.service.master.configuration.AtomicLimesClientRegistry;
import io.atomiclimes.data.service.master.configuration.AtomicLimesMasterProperties;
import io.atomiclimes.data.service.master.filters.AtomicLimesClientFilter;
import io.atomiclimes.data.service.master.logging.AtomicLimesMasterLogMessage;
import io.atomiclimes.date.service.client.enums.ClientType;

public class GuiClientFilter implements AtomicLimesClientFilter {

	private AtomicLimesMasterProperties properties;
	private AtomicLimesClientRegistry registry;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(GuiClientFilter.class);

	public GuiClientFilter(AtomicLimesClientRegistry registry, AtomicLimesMasterProperties properties) {
		this.properties = properties;
		this.registry = registry;
	}

	@Override
	public AtomicLimesRegistrationResponse filter(AtomicLimesClient client) {
		AtomicLimesRegistrationResponse registrationResponse = new AtomicLimesRegistrationResponse();
		registrationResponse.setHeartbeatWaitingTime(properties.getHeartbeatWaitingTime());
		registry.register(client);
		LOG.info(AtomicLimesMasterLogMessage.CLIENT_REGISTERED_LOG_MESSAGE, client.getName(),
				client.getType().toString());
		return registrationResponse;
	}

	@Override
	public ClientType getType() {
		return ClientType.GUI;
	}

}
