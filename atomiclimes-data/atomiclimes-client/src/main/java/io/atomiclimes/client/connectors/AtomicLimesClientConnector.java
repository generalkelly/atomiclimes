package io.atomiclimes.client.connectors;

import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;

public interface AtomicLimesClientConnector {

	public void connect();

	void read(AtomicLimesRegistrationResponse registrationResponse);

}
