package io.atomiclimes.agent.configuration;

import org.springframework.context.ApplicationListener;

import io.atomiclimes.agent.connectors.AtomicLimesAgentConnector;

public class AgentConnectedToMasterListener implements ApplicationListener<AgentConnectedToMasterEvent> {

	private AtomicLimesAgentConnector connector;

	public AgentConnectedToMasterListener(AtomicLimesAgentConnector connector) {
		this.connector = connector;
	}

	@Override
	public void onApplicationEvent(AgentConnectedToMasterEvent event) {
		connector.connect();
		connector.read(event.getRegistrationResponse());
	}

}
