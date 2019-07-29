package io.atomiclimes.agent.configuration;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.atomiclimes.agent.logging.AtomicLimesAgentLogMessage;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.communication.AtomicLimesAgent;
import io.atomiclimes.communication.AtomicLimesAgentHeartbeat;
import io.atomiclimes.communication.AtomicLimesRegistrationResponse;

public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private ApplicationEventPublisher applicationEventPublisher;
	private AtomicLimesAgentProperties properties;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(StartupListener.class);

	public StartupListener(ApplicationEventPublisher applicationEventPublisher,
			AtomicLimesAgentProperties properties) {
		this.applicationEventPublisher = applicationEventPublisher;
		this.properties = properties;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		AtomicLimesAgent agent = new AtomicLimesAgent();
		agent.setName(properties.getName());
		String hostAddress = null;
		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress("atomiclimes.io", 80));
			hostAddress = socket.getLocalAddress().getHostAddress();
		} catch (IOException e) {
			LOG.error(AtomicLimesAgentLogMessage.FAILED_TO_RETRIEVE_HOST_ADDRESS_LOG_MESSAGE, e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				LOG.error(AtomicLimesAgentLogMessage.FAILED_TO_CLOSE_SOCKET_LOG_MESSAGE, e);
			}
		}

		agent.setIp(hostAddress);
		agent.setPort(properties.getPort());

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<AtomicLimesAgent> request = new HttpEntity<>(agent);

		ResponseEntity<AtomicLimesRegistrationResponse> responseEntity = restTemplate
				.postForEntity(properties.getMasterUrl() + "register", request, AtomicLimesRegistrationResponse.class);

		AtomicLimesRegistrationResponse registrationResponse = responseEntity.getBody();
		System.out.println(registrationResponse.getKafkaConfiguration().getTopicName());

		RestTemplate heartbeatTemplate = new RestTemplate();
		HttpEntity<AtomicLimesAgentHeartbeat> heartbeatRequest = new HttpEntity<>(new AtomicLimesAgentHeartbeat(agent));

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable heartbeat = () -> {
			LOG.debug(AtomicLimesAgentLogMessage.SENDING_HEARTBEAT_LOG_MESSAGE, agent.getName().toUpperCase(),
					Integer.toString(registrationResponse.getHeartbeatWaitingTime()));
			heartbeatTemplate.postForObject(properties.getMasterUrl() + "heartbeat", heartbeatRequest,
					AtomicLimesAgent.class);
		};
		
		executor.scheduleWithFixedDelay(heartbeat, 10, registrationResponse.getHeartbeatWaitingTime(),
				TimeUnit.SECONDS);

		applicationEventPublisher.publishEvent(new AgentConnectedToMasterEvent(this, registrationResponse));
	}

}
