package io.atomiclimes.data.collector.listener.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.communication.AtomicLimesAgent;

public class NewAgentsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Map<String, AtomicLimesAgent> newAgents;

	public NewAgentsEvent(Object source, Map<String, AtomicLimesAgent> newAgents) {
		super(source);
		this.newAgents = newAgents;
	}

	public Map<String, AtomicLimesAgent> getNewAgents() {
		return newAgents;
	}

}
