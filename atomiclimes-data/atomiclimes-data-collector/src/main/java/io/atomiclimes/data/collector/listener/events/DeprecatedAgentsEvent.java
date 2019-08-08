package io.atomiclimes.data.collector.listener.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.communication.AtomicLimesAgent;

public class DeprecatedAgentsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Map<String, AtomicLimesAgent> deprecatedAgents;

	public DeprecatedAgentsEvent(Object source, Map<String, AtomicLimesAgent> deprecatedAgents) {
		super(source);
		this.deprecatedAgents = deprecatedAgents;
	}

	Map<String, AtomicLimesAgent> getDeprecatedAgents() {
		return this.deprecatedAgents;
	}

}
