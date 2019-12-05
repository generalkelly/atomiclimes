package io.atomiclimes.master.filters;

import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.atomiclimes.data.service.master.filters.AtomicLimesClientFilter;
import io.atomiclimes.date.service.client.enums.ClientType;

public class ProxyClientFilter implements AtomicLimesClientFilter {

	public ProxyClientFilter() {
	}

	@Override
	public AtomicLimesRegistrationResponse filter(AtomicLimesClient client) {
		AtomicLimesRegistrationResponse registrationResponse = new AtomicLimesRegistrationResponse();
		registrationResponse.setHeartbeatWaitingTime(1000);
		return registrationResponse;
	}

	@Override
	public ClientType getType() {
		return ClientType.PROXY;
	}

}
