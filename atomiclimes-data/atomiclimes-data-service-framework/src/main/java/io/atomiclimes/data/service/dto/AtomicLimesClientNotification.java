package io.atomiclimes.data.service.dto;

import io.atomiclimes.data.service.master.events.ClientNotificationType;

public class AtomicLimesClientNotification {

	public AtomicLimesClientNotification() {
	}

	private AtomicLimesClient client;
	private ClientNotificationType clientNotificationType;

	public AtomicLimesClientNotification(AtomicLimesClient client, ClientNotificationType clientNotificationType) {
		this.client = client;
		this.clientNotificationType = clientNotificationType;
	}

	public AtomicLimesClient getClient() {
		return client;
	}

	public ClientNotificationType getClientNotificationType() {
		return clientNotificationType;
	}

}
