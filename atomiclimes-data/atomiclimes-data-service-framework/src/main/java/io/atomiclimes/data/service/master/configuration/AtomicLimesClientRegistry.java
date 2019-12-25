package io.atomiclimes.data.service.master.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesClientHeartbeat;
import io.atomiclimes.data.service.dto.AtomicLimesClientNotification;
import io.atomiclimes.data.service.master.events.ClientNotificationType;
import io.atomiclimes.data.service.master.logging.AtomicLimesMasterLogMessage;
import io.atomiclimes.date.service.client.enums.ClientType;

public class AtomicLimesClientRegistry {

	private Map<String, AtomicLimesClient> registeredClients = new HashMap<>();
	private EnumMap<ClientType, List<AtomicLimesClient>> clientTypeSubsriptions = new EnumMap<>(ClientType.class);
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesClientRegistry.class);

	public void register(AtomicLimesClient client) {
		String key = client.getName().toUpperCase();
		OffsetDateTime creationTimestamp = OffsetDateTime.now();
		client.setLastKeepAlive(creationTimestamp);
		client.setUpTime(creationTimestamp);
		registeredClients.computeIfAbsent(key, k -> client);
		notifySubscribers(client, ClientNotificationType.CLIENT_REGISTERED);
	}

	private void notifySubscribers(AtomicLimesClient client, ClientNotificationType clientNotificationType) {
		List<AtomicLimesClient> subscribers = clientTypeSubsriptions.get(client.getType());
		if (subscribers != null) {
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<AtomicLimesClientNotification> request = new HttpEntity<>(
					new AtomicLimesClientNotification(client, clientNotificationType));
			for (AtomicLimesClient subscriber : subscribers) {
				URI url;
				try {
					url = new URI("http", null, subscriber.getIp(), subscriber.getPort(), "/clientNotification", null,
							null);
					restTemplate.postForLocation(url, request);
					LOG.info(AtomicLimesMasterLogMessage.SENT_NOTIFICATION_TO_SUBSCRIBER_LOG_MESSAGE, client.getName(),
							client.getType().toString(), subscriber.getName(), url.toString());

				} catch (URISyntaxException e) {
					LOG.error(AtomicLimesMasterLogMessage.NOTIFICATION_OF_SUBSCRIBER_FAILED, e, subscriber.getName());
				}
			}
		}
	}

	public void deregister(AtomicLimesClient client) {
		String key = client.getName().toUpperCase();
		registeredClients.remove(key);
		notifySubscribers(client, ClientNotificationType.CLIENT_DEREGISTERED);
	}

	public void agentDown(AtomicLimesClient client) {
		String key = client.getName().toUpperCase();
		registeredClients.get(key).setAlive(false);
		notifySubscribers(client, ClientNotificationType.AGENT_DOWN);
	}

	public void agentUp(AtomicLimesClientHeartbeat heartbeat) {
		String key = heartbeat.getName().toUpperCase();
		AtomicLimesClient client = registeredClients.get(key);
		if (!client.isAlive()) {
			client.setAlive(true);
			notifySubscribers(client, ClientNotificationType.AGENT_UP);
		}
		client.setLastKeepAlive(OffsetDateTime.now());
	}

	public Map<String, AtomicLimesClient> getRegisteredClients() {
		return registeredClients;
	}

	public void subscribe(ClientType topic, AtomicLimesClient subscriber) {
		List<AtomicLimesClient> subscribers = clientTypeSubsriptions.get(topic);
		if (subscribers == null) {
			subscribers = new LinkedList<>();
		}
		subscribers.add(subscriber);
		LOG.info(AtomicLimesMasterLogMessage.ADDED_SUBSCRIBER_TO_TOPIC_LOG_MESSAGE, subscriber.getName(),
				topic.toString());
		clientTypeSubsriptions.put(topic, subscribers);
	}

}
