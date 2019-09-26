package io.atomiclimes.data.service.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.master.configuration.AtomicLimesKafkaProperties;
import io.atomiclimes.data.service.master.logging.AtomicLimesMasterLogMessage;

public class AtomicLimesKafkaAdministration {

	private AtomicLimesKafkaProperties atomicLimesKafkaProperties;
	private final Map<String, Object> kafkaPropertiesMap;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesKafkaAdministration.class);

	public AtomicLimesKafkaAdministration(AtomicLimesKafkaProperties atomicLimesKafkaProperties) {
		this.atomicLimesKafkaProperties = atomicLimesKafkaProperties;
		this.kafkaPropertiesMap = atomicLimesKafkaProperties.createKafkaPropertiesMapWithBootstrapServer();
	}

	public Map<String, Object> getKafkaConfigurationIfExistentForAgent(AtomicLimesClient client) {
		Map<String, Object> kafkaConfiguration = null;

		Set<String> listTopics = null;
		try (AdminClient adminClient = AdminClient.create(kafkaPropertiesMap)) {
			ListTopicsResult listTopicsResult = adminClient.listTopics();
			listTopics = listTopicsResult.names().get();
		} catch (InterruptedException | ExecutionException e) {
			LOG.info(AtomicLimesMasterLogMessage.COULD_NOT_RETRIEVE_TOPICS_LOG_MESSAGE);
			Thread.currentThread().interrupt();
		}

		if (listTopics != null && topicExists(client, listTopics)) {
			kafkaConfiguration = generateKafkaConfigurationForAgent(client);
		}
		return kafkaConfiguration;
	}

	private boolean topicExists(AtomicLimesClient client, Set<String> listTopics) {
		return listTopics.stream().anyMatch(topicName -> topicName.equalsIgnoreCase(client.getName()));
	}

	public Map<String, Object> createTopic(AtomicLimesClient agent) {
		Map<String, Object> kafkaConfiguration = new HashMap<>();
		try (AdminClient adminClient = AdminClient
				.create(atomicLimesKafkaProperties.createKafkaPropertiesMapWithBootstrapServer())) {

			kafkaConfiguration = generateKafkaConfigurationForAgent(agent);

			String topicName = generateTopicName(agent);
			NewTopic topic = new NewTopic(topicName, 1, (short) 1);
			List<NewTopic> topics = new ArrayList<>();
			topics.add(topic);
			CreateTopicsResult result = adminClient.createTopics(topics);
			result.all().get();

		} catch (Exception e) {
			LOG.error(AtomicLimesMasterLogMessage.KAFKA_ADMINISTRATION_FAILURE_LOG_MESSAGE, e, agent.getName());
		}
		return kafkaConfiguration;
	}

	private Map<String, Object> generateKafkaConfigurationForAgent(AtomicLimesClient agent) {
		Map<String, Object> kafkaConfiguration = new HashMap<>();
		String topicName = generateTopicName(agent);
		kafkaConfiguration.put("topic.name", topicName);
		kafkaConfiguration.put("bootstrap.servers", atomicLimesKafkaProperties.getBootstrapServer());
		kafkaConfiguration.putAll(atomicLimesKafkaProperties.createKafkaPropertiesMap());
		return kafkaConfiguration;
	}

	private String generateTopicName(AtomicLimesClient agent) {
		return agent.getName().toUpperCase();
	}

}
