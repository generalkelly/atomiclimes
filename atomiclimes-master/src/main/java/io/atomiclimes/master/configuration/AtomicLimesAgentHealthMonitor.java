package io.atomiclimes.master.configuration;

import java.time.OffsetDateTime;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.communication.AtomicLimesAgent;
import io.atomiclimes.master.logging.AtomicLimesMasterLogMessage;

public class AtomicLimesAgentHealthMonitor {

	private AtomicLimesAgentRegistry agentRegistry;
	private AtomicLimesMasterProperties properties;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesAgentHealthMonitor.class);

	public AtomicLimesAgentHealthMonitor(AtomicLimesAgentRegistry agentRegistry,
			AtomicLimesMasterProperties properties) {
		this.agentRegistry = agentRegistry;
		this.properties = properties;
	}

	public void checkHealth() {
		agentRegistry.getRegisteredAgents().forEach((name, agent) -> alterAgentHealthIfNecessarry(agent));
	}

	private void alterAgentHealthIfNecessarry(AtomicLimesAgent agent) {
		if (agent.isAlive() && agent.getLastKeepAlive()
				.isBefore(OffsetDateTime.now().minusSeconds(this.properties.getHeartbeatWaitingTime()))) {
			agent.setAlive(false);
			LOG.info(AtomicLimesMasterLogMessage.AGENT_DOWN_LOG_MESSAGE, agent.getName().toUpperCase(),
					Integer.toString(this.properties.getHeartbeatWaitingTime()));
		}
	}

}
