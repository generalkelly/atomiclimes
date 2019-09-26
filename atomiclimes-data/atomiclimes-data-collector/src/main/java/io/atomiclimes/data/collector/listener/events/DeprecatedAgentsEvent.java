package io.atomiclimes.data.collector.listener.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.data.service.dto.AtomicLimesClient;


public class DeprecatedAgentsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Map<String, AtomicLimesClient> deprecatedAgents;

	public DeprecatedAgentsEvent(Object source, Map<String, AtomicLimesClient> deprecatedAgents) {
		super(source);
		this.deprecatedAgents = deprecatedAgents;
	}

	Map<String, AtomicLimesClient> getDeprecatedAgents() {
		return this.deprecatedAgents;
	}

}
