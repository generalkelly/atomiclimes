package io.atomiclimes.master.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.communication.AtomicLimesAgent;
import io.atomiclimes.communication.KafkaConfiguration;
import io.atomiclimes.master.configuration.AtomicLimesKafkaProperties;
import io.atomiclimes.master.logging.AtomicLimesMasterLogMessage;

public class AtomicLimesKafkaAdministration {

	private AtomicLimesKafkaProperties atomicLimesKafkaProperties;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesKafkaAdministration.class);

	public AtomicLimesKafkaAdministration(AtomicLimesKafkaProperties atomicLimesKafkaProperties) {
		this.atomicLimesKafkaProperties = atomicLimesKafkaProperties;
	}

	public KafkaConfiguration getKafkaConfigurationIfExistentForAgent(AtomicLimesAgent agent) {
		KafkaConfiguration kafkaConfiguration = null;

		Set<String> listTopics = null;
		AdminClient adminClient = null;
		try {
			adminClient = AdminClient.create(atomicLimesKafkaProperties.createKafkaPropertiesMapWithBootstrapServer());
			ListTopicsResult listTopicsResult = adminClient.listTopics();
			listTopics = listTopicsResult.names().get();
		} catch (InterruptedException | ExecutionException e) {
			LOG.info(AtomicLimesMasterLogMessage.COULD_NOT_RETRIEVE_TOPICS_LOG_MESSAGE);
			Thread.currentThread().interrupt();
		} finally {
			if (adminClient != null) {
				adminClient.close();
			}
		}

		if (listTopics != null && topicExists(agent, listTopics)) {
			String topicName = generateTopicName(agent);
			kafkaConfiguration = new KafkaConfiguration();
			kafkaConfiguration.setBootstrapServer(atomicLimesKafkaProperties.getBootstrapServer());
			kafkaConfiguration.setTopicName(topicName);
			kafkaConfiguration.setKafkaProperties(atomicLimesKafkaProperties.createKafkaPropertiesMap());
		}
		return kafkaConfiguration;
	}

	private boolean topicExists(AtomicLimesAgent agent, Set<String> listTopics) {
		return listTopics.stream().anyMatch(topicName -> topicName.equalsIgnoreCase(agent.getName()));
	}

	public KafkaConfiguration createTopic(AtomicLimesAgent agent) {
		AdminClient adminClient = null;
		KafkaConfiguration kafkaConfiguration = null;
		try {
			adminClient = AdminClient.create(atomicLimesKafkaProperties.createKafkaPropertiesMapWithBootstrapServer());
			String topicName = generateTopicName(agent);
			kafkaConfiguration = new KafkaConfiguration();
			kafkaConfiguration.setBootstrapServer(atomicLimesKafkaProperties.getBootstrapServer());
			kafkaConfiguration.setTopicName(topicName);
			kafkaConfiguration.setKafkaProperties(atomicLimesKafkaProperties.createKafkaPropertiesMap());
			NewTopic topic = new NewTopic(topicName, 1, (short) 1);

			List<NewTopic> topics = new ArrayList<>();
			topics.add(topic);

			CreateTopicsResult result = adminClient.createTopics(topics);
			result.all().get();

		} catch (Exception e) {
			LOG.error(AtomicLimesMasterLogMessage.KAFKA_ADMINISTRATION_FAILURE_LOG_MESSAGE, e, agent.getName());
		} finally {
			if (adminClient != null) {
				adminClient.close();
			}
		}
		return kafkaConfiguration;
	}

	private String generateTopicName(AtomicLimesAgent agent) {
		return agent.getName().toUpperCase();
	}

}
