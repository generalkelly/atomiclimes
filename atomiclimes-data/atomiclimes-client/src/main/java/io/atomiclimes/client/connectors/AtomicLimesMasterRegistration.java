package io.atomiclimes.client.connectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.atomiclimes.client.configuration.AtomicLimesClientProperties;
import io.atomiclimes.client.logging.AtomicLimesClientLogMessage;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;

public class AtomicLimesMasterRegistration {

	private AtomicLimesClientProperties properties;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesMasterRegistration.class);

	public AtomicLimesMasterRegistration(AtomicLimesClientProperties properties) {
		this.properties = properties;
	}

	public AtomicLimesRegistrationResponse register(AtomicLimesClient client) {

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<AtomicLimesClient> request = new HttpEntity<>(client);

		ResponseEntity<AtomicLimesRegistrationResponse> responseEntity = null;
		try {
			responseEntity = restTemplate.postForEntity(properties.getMasterUrl() + "register", request,
					AtomicLimesRegistrationResponse.class);
			LOG.info(AtomicLimesClientLogMessage.REGISTERED_CLIENT_LOG_MESSAGE, client.getName());
		} catch (RestClientException e) {
			LOG.info(AtomicLimesClientLogMessage.REGISTERING_CLIENT_FAILED_LOG_MESSAGE, client.getName());
			LOG.debug(AtomicLimesClientLogMessage.REGISTERING_CLIENT_FAILED_LOG_MESSAGE, e, client.getName());
			try {
				Thread.sleep(1000);
				this.register(client);
			} catch (InterruptedException e1) {
				Thread.currentThread().interrupt();
			}
			return null;
		}
		return responseEntity.getBody();

	}

}
