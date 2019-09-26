package io.atomiclimes.data.service.master.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesClientHeartbeat;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.atomiclimes.data.service.master.configuration.AtomicLimesClientCollection;
import io.atomiclimes.data.service.master.configuration.AtomicLimesClientRegistry;
import io.atomiclimes.data.service.master.filters.AtomicLimesClientFilter;
import io.atomiclimes.data.service.master.logging.AtomicLimesMasterLogMessage;
import io.atomiclimes.date.service.client.enums.ClientType;

@RestController
public class AtomicLimesMasterController {
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesMasterController.class);

	@Autowired
	private AtomicLimesClientRegistry registry;

	@Autowired
	AtomicLimesClientCollection atomicLimesClientCollection;

	@GetMapping(value = "/listAgents")
	public Map<String, AtomicLimesClient> listAgents() {
		return registry.getRegisteredClients().entrySet().stream()
				.filter(entry -> entry.getValue().getType().equals(ClientType.AGENT))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	@GetMapping(value = "/listClients")
	public Map<String, AtomicLimesClient> listClients() {
		return registry.getRegisteredClients();
	}

	@PostMapping(value = "/register")
	public AtomicLimesRegistrationResponse register(@RequestBody AtomicLimesClient client) {
		return registerClient(client);
	}

	private AtomicLimesRegistrationResponse registerClient(AtomicLimesClient client) {
		AtomicLimesClientFilter clientFilter = this.atomicLimesClientCollection.getClientFilterMap()
				.get(client.getType());
		if (clientFilter != null) {
			return clientFilter.filter(client);
		} else {
			return null;
		}
	}

	@PostMapping(value = "/deregister")
	public void deregisterAgent(@RequestBody AtomicLimesClient client) {
		registry.deregister(client);
	}

	@PostMapping(value = "/heartbeat")
	public void heartbeat(@RequestBody AtomicLimesClientHeartbeat heartbeat) {
		LOG.info(AtomicLimesMasterLogMessage.RECEIVED_HARTBEAT_LOG_MESSAGE, heartbeat.getName().toUpperCase());
		registry.agentUp(heartbeat);
	}

}
