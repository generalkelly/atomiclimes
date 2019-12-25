package io.atomiclimes.client.notification.filter;

import java.util.Set;

import io.atomiclimes.data.service.dto.AtomicLimesClientNotification;

public class AtomicLimesClientNotificationFilterRegistry implements ClientNotificationFilter {

	private Set<ClientNotificationFilter> clientNotificationFilters;

	public AtomicLimesClientNotificationFilterRegistry(Set<ClientNotificationFilter> clientNotificationFilters) {
		this.clientNotificationFilters = clientNotificationFilters;
	}

	@Override
	public void filter(AtomicLimesClientNotification clientNotification) {
		for (ClientNotificationFilter notificationFilter : this.clientNotificationFilters) {
			notificationFilter.filter(clientNotification);
		}
	}

}
