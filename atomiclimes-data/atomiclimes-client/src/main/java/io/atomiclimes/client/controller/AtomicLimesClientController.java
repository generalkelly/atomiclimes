package io.atomiclimes.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.atomiclimes.client.configuration.AtomicLimesClientProperties;
import io.atomiclimes.client.notification.filter.AtomicLimesClientNotificationFilterRegistry;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesClientNotification;
import io.atomiclimes.date.service.client.enums.ClientType;

@RestController
public class AtomicLimesClientController {

	private AtomicLimesClientProperties properties;
	private AtomicLimesClientNotificationFilterRegistry atomicLimesClientNotificationFilterRegistry;

	public AtomicLimesClientController(AtomicLimesClientProperties properties, AtomicLimesClientNotificationFilterRegistry atomicLimesClientNotificationFilterRegistry) {
		this.properties = properties;
		this.atomicLimesClientNotificationFilterRegistry = atomicLimesClientNotificationFilterRegistry;
	}

	@GetMapping(value = "sharedProperties")
	public Map<String, Object> getSharedProperties(@RequestBody AtomicLimesClient client) {
		if (isAuthorized(client)) {
			return this.properties.getSharedProperties();
		} else {
			return new HashMap<>();
		}
	}

	@PostMapping(value = "clientNotification")
	public void clientNotification(@RequestBody AtomicLimesClientNotification clientNotification) {
		atomicLimesClientNotificationFilterRegistry.filter(clientNotification);
	}

	private boolean isAuthorized(AtomicLimesClient client) {
		List<ClientType> authorizedClientTypes = this.properties.getAuthorizedClientTypes();
		return authorizedClientTypes.contains(client.getType());
	}

}
