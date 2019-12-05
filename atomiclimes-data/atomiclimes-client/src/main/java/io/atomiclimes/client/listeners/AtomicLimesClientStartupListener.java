package io.atomiclimes.client.listeners;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import io.atomiclimes.client.configuration.AtomicLimesClientProperties;
import io.atomiclimes.client.connectors.AtomicLimesMasterRegistration;
import io.atomiclimes.client.events.ClientConnectedToMasterEvent;
import io.atomiclimes.client.logging.AtomicLimesClientLogMessage;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesClientHeartbeat;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;

public class AtomicLimesClientStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private ApplicationEventPublisher applicationEventPublisher;
	private AtomicLimesClientProperties properties;
	private AtomicLimesClient client;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesClientStartupListener.class);

	public AtomicLimesClientStartupListener(ApplicationEventPublisher applicationEventPublisher,
			AtomicLimesClientProperties properties, AtomicLimesClient client) {
		this.applicationEventPublisher = applicationEventPublisher;
		this.properties = properties;
		this.client = client;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		setClientProperties(this.client);
		AtomicLimesMasterRegistration registration = new AtomicLimesMasterRegistration(this.properties);
		AtomicLimesRegistrationResponse registrationResponse = registration.register(this.client);
		this.startHeartbeat(this.client, registrationResponse);
		applicationEventPublisher.publishEvent(new ClientConnectedToMasterEvent(this, registrationResponse));
	}

	private void setClientProperties(AtomicLimesClient client) {
		client.setName(this.properties.getName());
		client.setIp(getHostAdress());
	}

	private static String getHostAdress() {
		String hostAddress = "unknown";
		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress("mpohland.de", 80));
			hostAddress = socket.getLocalAddress().getHostAddress();
		} catch (IOException e) {
			LOG.error(AtomicLimesClientLogMessage.FAILED_TO_RETRIEVE_HOST_ADDRESS_LOG_MESSAGE, e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				LOG.error(AtomicLimesClientLogMessage.FAILED_TO_CLOSE_SOCKET_LOG_MESSAGE, e);
			}
		}
		return hostAddress;
	}

	private void startHeartbeat(AtomicLimesClient client, AtomicLimesRegistrationResponse registrationResponse) {
		RestTemplate heartbeatTemplate = new RestTemplate();
		HttpEntity<AtomicLimesClientHeartbeat> heartbeatRequest = new HttpEntity<>(
				new AtomicLimesClientHeartbeat(client));
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable heartbeat = () -> {
			LOG.debug(AtomicLimesClientLogMessage.SENDING_HEARTBEAT_LOG_MESSAGE, client.getName().toUpperCase(),
					Integer.toString(registrationResponse.getHeartbeatWaitingTime()));
			heartbeatTemplate.postForObject(properties.getMasterUrl() + "heartbeat", heartbeatRequest,
					AtomicLimesClient.class);
		};

		executor.scheduleWithFixedDelay(heartbeat, 10, registrationResponse.getHeartbeatWaitingTime(),
				TimeUnit.SECONDS);
	}

}
