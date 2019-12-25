package io.atomiclimes.master.filters;

import java.util.Map;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.atomiclimes.data.service.master.configuration.AtomicLimesClientRegistry;
import io.atomiclimes.data.service.master.configuration.AtomicLimesMasterProperties;
import io.atomiclimes.data.service.master.controller.AtomicLimesKafkaAdministration;
import io.atomiclimes.data.service.master.filters.AtomicLimesClientFilter;
import io.atomiclimes.data.service.master.logging.AtomicLimesMasterLogMessage;
import io.atomiclimes.date.service.client.enums.ClientType;

public class AgentClientFilter implements AtomicLimesClientFilter {

	private AtomicLimesClientRegistry registry;
	private AtomicLimesMasterProperties properties;
	private AtomicLimesKafkaAdministration kafkaAdministration;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AgentClientFilter.class);

	public AgentClientFilter(AtomicLimesClientRegistry registry, AtomicLimesMasterProperties properties,
			AtomicLimesKafkaAdministration kafkaAdministration) {
		this.registry = registry;
		this.properties = properties;
		this.kafkaAdministration = kafkaAdministration;
	}

	@Override
	public AtomicLimesRegistrationResponse filter(AtomicLimesClient client) {
		AtomicLimesRegistrationResponse registrationResponse = new AtomicLimesRegistrationResponse();

		Map<String, Object> kafkaConfiguration;
		registry.register(client);
		LOG.info(AtomicLimesMasterLogMessage.CLIENT_REGISTERED_LOG_MESSAGE, client.getName().toUpperCase(),
				client.getType().toString());

		kafkaConfiguration = kafkaAdministration.getKafkaConfigurationIfExistentForAgent(client);
		if (kafkaConfiguration != null) {
			LOG.info(AtomicLimesMasterLogMessage.KAFKA_TOPIC_ALLREADY_EXISTS_FOR_AGENT_LOG_MESSAGE,
					kafkaConfiguration.get("topic.name").toString(), client.getName());
		} else {
			kafkaConfiguration = kafkaAdministration.createTopic(client);
		}
		if (kafkaConfiguration != null) {
			registrationResponse.setKafkaConfiguration(kafkaConfiguration);
		} else {
			LOG.error(AtomicLimesMasterLogMessage.KAFKA_CONFIGURATION_ERROR_LOG_MESSAGE);
		}
		registrationResponse.setHeartbeatWaitingTime(properties.getHeartbeatWaitingTime());
		return registrationResponse;
	}

	@Override
	public ClientType getType() {
		return ClientType.AGENT;
	}

}
