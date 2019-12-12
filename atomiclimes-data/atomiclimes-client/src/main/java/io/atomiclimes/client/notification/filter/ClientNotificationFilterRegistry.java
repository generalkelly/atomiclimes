package io.atomiclimes.client.notification.filter;

import java.util.Set;

import io.atomiclimes.data.service.dto.AtomicLimesClientNotification;

public class ClientNotificationFilterRegistry implements ClientNotificationFilter {

	private Set<ClientNotificationFilter> clientNotificationFilters;

	public ClientNotificationFilterRegistry(Set<ClientNotificationFilter> clientNotificationFilters) {
		this.clientNotificationFilters = clientNotificationFilters;
	}

	@Override
	public void filter(AtomicLimesClientNotification clientNotification) {
		clientNotificationFilters.stream().forEach(filter -> filter.filter(clientNotification));
	}

}
