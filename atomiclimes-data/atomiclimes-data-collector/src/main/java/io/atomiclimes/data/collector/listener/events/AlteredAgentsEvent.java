package io.atomiclimes.data.collector.listener.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.communication.AtomicLimesAgent;

public class AlteredAgentsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private Map<String, AtomicLimesAgent> alteredAgents;

	public AlteredAgentsEvent(Object source, Map<String, AtomicLimesAgent> alteredAgents) {
		super(source);
		this.alteredAgents = alteredAgents;
	}

	public Map<String, AtomicLimesAgent> getAlteredAgents() {
		return alteredAgents;
	}

}
