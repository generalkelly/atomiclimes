package io.atomiclimes.data.collector.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.client.RestTemplate;

import io.atomiclimes.common.helper.maputils.MapComparator;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.collector.listener.events.AlteredAgentsEvent;
import io.atomiclimes.data.collector.listener.events.DeprecatedAgentsEvent;
import io.atomiclimes.data.collector.listener.events.NewAgentsEvent;
import io.atomiclimes.data.service.dto.AtomicLimesClient;

public class AtomicLimesaDataCollectorAgentRegistry {

	private AtomicLimesDataCollectorProperties properties;
	private Map<String, AtomicLimesClient> agentMap = new HashMap<>();
	private ApplicationEventPublisher applicationEventPublisher;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesaDataCollectorAgentRegistry.class);

	public AtomicLimesaDataCollectorAgentRegistry(AtomicLimesDataCollectorProperties properties,
			ApplicationEventPublisher applicationEventPublisher) {
		this.properties = properties;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void poll() {
		ScheduledExecutorService pollingExecutor = Executors.newScheduledThreadPool(1);
		Runnable pollAgentList = () -> {
			Map<String, AtomicLimesClient> agentMapFromMaster = this.retrieveAgentMap();
			Map<String, AtomicLimesClient> newAgents = MapComparator.leftDiff(agentMapFromMaster, agentMap);
			Map<String, AtomicLimesClient> deprecatedAgents = MapComparator.leftDiff(agentMap, agentMapFromMaster);
			Map<String, AtomicLimesClient> alteredAgents = MapComparator.diffValuesWithCommonKeys(agentMap,
					agentMapFromMaster);

			this.agentMap = agentMapFromMaster;
			if (!newAgents.isEmpty()) {
				LOG.info("found new agents");
				applicationEventPublisher.publishEvent(new NewAgentsEvent(this, newAgents));
			}else {
				LOG.info("no new agents");
			}
			if (!deprecatedAgents.isEmpty()) {
				applicationEventPublisher.publishEvent(new DeprecatedAgentsEvent(this, deprecatedAgents));
			}
			if (!alteredAgents.isEmpty()) {
				applicationEventPublisher.publishEvent(new AlteredAgentsEvent(this, alteredAgents));
			}
		};
		pollingExecutor.scheduleWithFixedDelay(pollAgentList, 10, 10, TimeUnit.SECONDS);
		
	}

	@SuppressWarnings("unchecked")
	private Map<String, AtomicLimesClient> retrieveAgentMap() {
		RestTemplate restTemplate = new RestTemplate();
		
		LOG.info("asking for agentList");
		return (Map<String, AtomicLimesClient>) restTemplate.getForObject(properties.getAgentListUrl(), Map.class);
	}

}
