package io.atomiclimes.master.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.communication.AtomicLimesAgent;
import io.atomiclimes.communication.AtomicLimesAgentHeartbeat;
import io.atomiclimes.communication.AtomicLimesRegistrationResponse;
import io.atomiclimes.communication.KafkaConfiguration;
import io.atomiclimes.master.configuration.AtomicLimesAgentRegistry;
import io.atomiclimes.master.configuration.AtomicLimesMasterProperties;
import io.atomiclimes.master.logging.AtomicLimesMasterLogMessage;

@RestController
public class AtomicLimesMasterController {
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesMasterController.class);

	@Autowired
	private AtomicLimesAgentRegistry registry;

	@Autowired
	private AtomicLimesMasterProperties properties;

	@Autowired
	private AtomicLimesKafkaAdministration atomicLimesKafkaAdministration;

	@GetMapping(value = "/listAgents")
	public Map<String, AtomicLimesAgent> listAgents() {
		return registry.getRegisteredAgents();
	}

	@PostMapping(value = "/register")
	public AtomicLimesRegistrationResponse registerAgent(@RequestBody AtomicLimesAgent agent) {
		AtomicLimesRegistrationResponse registrationResponse = new AtomicLimesRegistrationResponse();

		KafkaConfiguration kafkaConfiguration;
		registry.register(agent);
		LOG.info(AtomicLimesMasterLogMessage.AGENT_REGISTERED_LOG_MESSAGE, agent.getName().toUpperCase());

		kafkaConfiguration = atomicLimesKafkaAdministration.getKafkaConfigurationIfExistentForAgent(agent);
		if (kafkaConfiguration != null) {
			LOG.info(AtomicLimesMasterLogMessage.KAFKA_TOPIC_ALLREADY_EXISTS_FOR_AGENT_LOG_MESSAGE,
					kafkaConfiguration.getTopicName(), agent.getName());
		} else {
			kafkaConfiguration = atomicLimesKafkaAdministration.createTopic(agent);
		}
		if (kafkaConfiguration != null) {
			registrationResponse.setKafkaConfiguration(kafkaConfiguration);
		} else {
			LOG.error(AtomicLimesMasterLogMessage.KAFKA_CONFIGURATION_ERROR_LOG_MESSAGE);
		}
		registrationResponse.setHeartbeatWaitingTime(properties.getHeartbeatWaitingTime());
		return registrationResponse;
	}

	@PostMapping(value = "/deregister")
	public void deregisterAgent(@RequestBody AtomicLimesAgent agent) {
		registry.deregister(agent);
	}

	@PostMapping(value = "/heartbeat")
	public void heartbeat(@RequestBody AtomicLimesAgentHeartbeat heartbeat) {
		LOG.info(AtomicLimesMasterLogMessage.RECEIVED_HARTBEAT_LOG_MESSAGE, heartbeat.getName().toUpperCase());
		registry.agentUp(heartbeat);
	}

}
