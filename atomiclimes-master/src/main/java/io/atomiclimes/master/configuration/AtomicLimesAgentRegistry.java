package io.atomiclimes.master.configuration;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import io.atomiclimes.communication.AtomicLimesAgent;
import io.atomiclimes.communication.AtomicLimesAgentHeartbeat;

public class AtomicLimesAgentRegistry {

	private Map<String, AtomicLimesAgent> registeredAgents = new HashMap<>();

	public void register(AtomicLimesAgent agent) {
		String key = agent.getName().toUpperCase();
		OffsetDateTime creationTimestamp = OffsetDateTime.now();
		agent.setLastKeepAlive(creationTimestamp);
		agent.setUpTime(creationTimestamp);
		registeredAgents.computeIfAbsent(key, k -> agent);
	}

	public void deregister(AtomicLimesAgent agent) {
		String key = agent.getName().toUpperCase();
		registeredAgents.remove(key);
	}

	public void agentDown(AtomicLimesAgent agent) {
		String key = agent.getName().toUpperCase();
		registeredAgents.get(key).setAlive(false);
	}

	public void agentUp(AtomicLimesAgentHeartbeat heartbeat) {
		String key = heartbeat.getName().toUpperCase();
		AtomicLimesAgent agent = registeredAgents.get(key);
		agent.setAlive(true);
		agent.setLastKeepAlive(OffsetDateTime.now());
	}

	public Map<String, AtomicLimesAgent> getRegisteredAgents() {
		return registeredAgents;
	}

}
