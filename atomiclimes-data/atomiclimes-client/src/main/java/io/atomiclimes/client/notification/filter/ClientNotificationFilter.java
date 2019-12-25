package io.atomiclimes.client.notification.filter;

import io.atomiclimes.data.service.dto.AtomicLimesClientNotification;

public interface ClientNotificationFilter {

	public void filter(AtomicLimesClientNotification clientNotification);

}
