package io.atomiclimes.web.proxy;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomiclimes.client.notification.filter.ClientNotificationFilter;
import io.atomiclimes.data.service.master.events.ClientNotificationType;
import io.atomiclimes.web.proxy.routes.AtomicLimesZuulRoutesAdministration;

@Configuration
public class AtomicLimesReverseProxyConfiguration {

	@Bean
	ClientNotificationFilter clientRegisteredFilter(AtomicLimesZuulRoutesAdministration zuulRoutesAdministration) {
		return clientNotification -> {
			if (clientNotification.getClientNotificationType().equals(ClientNotificationType.CLIENT_REGISTERED)) {
				zuulRoutesAdministration.add(clientNotification.getClient());
			}
		};
	}

	@Bean
	ClientNotificationFilter clientDeregisteredFilter(AtomicLimesZuulRoutesAdministration zuulRoutesAdministration) {
		return clientNotification -> {
			if (clientNotification.getClientNotificationType().equals(ClientNotificationType.CLIENT_REGISTERED)) {
				zuulRoutesAdministration.remove(clientNotification.getClient());
			}
		};
	}

	@Bean
	AtomicLimesZuulRoutesAdministration zuulRoutesAdministration(ZuulProperties zuulProperties) {
		return new AtomicLimesZuulRoutesAdministration(zuulProperties);
	}

}
