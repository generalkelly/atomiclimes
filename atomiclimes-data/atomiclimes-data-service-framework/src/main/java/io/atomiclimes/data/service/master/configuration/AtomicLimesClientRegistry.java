package io.atomiclimes.data.service.master.configuration;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesClientHeartbeat;

public class AtomicLimesClientRegistry {

	private Map<String, AtomicLimesClient> registeredClients = new HashMap<>();

	public void register(AtomicLimesClient client) {
		String key = client.getName().toUpperCase();
		OffsetDateTime creationTimestamp = OffsetDateTime.now();
		client.setLastKeepAlive(creationTimestamp);
		client.setUpTime(creationTimestamp);
		registeredClients.computeIfAbsent(key, k -> client);
	}

	public void deregister(AtomicLimesClient client) {
		String key = client.getName().toUpperCase();
		registeredClients.remove(key);
	}

	public void agentDown(AtomicLimesClient client) {
		String key = client.getName().toUpperCase();
		registeredClients.get(key).setAlive(false);
	}

	public void agentUp(AtomicLimesClientHeartbeat heartbeat) {
		String key = heartbeat.getName().toUpperCase();
		AtomicLimesClient client = registeredClients.get(key);
		client.setAlive(true);
		client.setLastKeepAlive(OffsetDateTime.now());
	}

	public Map<String, AtomicLimesClient> getRegisteredClients() {
		return registeredClients;
	}

}
