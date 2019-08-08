package io.atomiclimes.agent.configuration;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.communication.AtomicLimesRegistrationResponse;

public class AgentConnectedToMasterEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private AtomicLimesRegistrationResponse registrationResponse;

	public AgentConnectedToMasterEvent(Object source, AtomicLimesRegistrationResponse registrationResponse) {
		super(source);
		this.registrationResponse = registrationResponse;
	}

	public AtomicLimesRegistrationResponse getRegistrationResponse() {
		return registrationResponse;
	}

}
