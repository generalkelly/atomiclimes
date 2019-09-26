package io.atomiclimes.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.atomiclimes.client.configuration.AtomicLimesClientProperties;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

@RestController
public class AtomicLimesClientController {

	private AtomicLimesClientProperties properties;

	public AtomicLimesClientController(AtomicLimesClientProperties properties) {
		this.properties = properties;
	}

	@GetMapping(value = "sharedProperties")
	public Map<String, Object> getSharedProperties(@RequestBody AtomicLimesClient client) {
		if (isAuthorized(client)) {
			return this.properties.getSharedProperties();
		} else {
			return new HashMap<>();
		}
	}

	private boolean isAuthorized(AtomicLimesClient client) {
		List<ClientType> authorizedClientTypes = this.properties.getAuthorizedClientTypes();
		return authorizedClientTypes.contains(client.getType());
	}

}
