package io.atomiclimes.agent.connectors;

import io.atomiclimes.communication.AtomicLimesRegistrationResponse;

public interface AtomicLimesAgentConnector {

	public void connect();

	void read(AtomicLimesRegistrationResponse registrationResponse);

}
