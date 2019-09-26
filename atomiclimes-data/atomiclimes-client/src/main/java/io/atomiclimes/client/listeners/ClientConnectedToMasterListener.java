package io.atomiclimes.client.listeners;

import org.springframework.context.ApplicationListener;

import io.atomiclimes.client.connectors.AtomicLimesClientConnector;
import io.atomiclimes.client.events.ClientConnectedToMasterEvent;

public class ClientConnectedToMasterListener implements ApplicationListener<ClientConnectedToMasterEvent> {

	private AtomicLimesClientConnector connector;

	public ClientConnectedToMasterListener(AtomicLimesClientConnector connector) {
		this.connector = connector;
	}

	@Override
	public void onApplicationEvent(ClientConnectedToMasterEvent event) {
		connector.connect();
		connector.read(event.getRegistrationResponse());
	}

}
