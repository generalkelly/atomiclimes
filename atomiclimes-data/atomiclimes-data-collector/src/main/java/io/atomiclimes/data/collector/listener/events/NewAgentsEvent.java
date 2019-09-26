package io.atomiclimes.data.collector.listener.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.data.service.dto.AtomicLimesClient;


public class NewAgentsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Map<String, AtomicLimesClient> newAgents;

	public NewAgentsEvent(Object source, Map<String, AtomicLimesClient> newAgents) {
		super(source);
		this.newAgents = newAgents;
	}

	public Map<String, AtomicLimesClient> getNewAgents() {
		return newAgents;
	}

}
