package io.atomiclimes.data.collector.listener.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.data.service.dto.AtomicLimesClient;

public class AlteredAgentsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private Map<String, AtomicLimesClient> alteredAgents;

	public AlteredAgentsEvent(Object source, Map<String, AtomicLimesClient> alteredAgents) {
		super(source);
		this.alteredAgents = alteredAgents;
	}

	public Map<String, AtomicLimesClient> getAlteredAgents() {
		return alteredAgents;
	}

}
