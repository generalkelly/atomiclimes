package io.atomiclimes.data.service.master.configuration;

import java.time.OffsetDateTime;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.master.logging.AtomicLimesMasterLogMessage;

public class AtomicLimesAgentHealthMonitor {

	private AtomicLimesClientRegistry clientRegistry;
	private AtomicLimesMasterProperties properties;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesAgentHealthMonitor.class);

	public AtomicLimesAgentHealthMonitor(AtomicLimesClientRegistry agentRegistry,
			AtomicLimesMasterProperties properties) {
		this.clientRegistry = agentRegistry;
		this.properties = properties;
	}

	public void checkHealth() {
		clientRegistry.getRegisteredClients().forEach((name, client) -> alterAgentHealthIfNecessarry(client));
	}

	private void alterAgentHealthIfNecessarry(AtomicLimesClient client) {
		if (client.isAlive() && client.getLastKeepAlive()
				.isBefore(OffsetDateTime.now().minusSeconds(this.properties.getHeartbeatWaitingTime()))) {
			client.setAlive(false);
			LOG.info(AtomicLimesMasterLogMessage.AGENT_DOWN_LOG_MESSAGE, client.getName().toUpperCase(),
					Integer.toString(this.properties.getHeartbeatWaitingTime()));
		}
	}

}
