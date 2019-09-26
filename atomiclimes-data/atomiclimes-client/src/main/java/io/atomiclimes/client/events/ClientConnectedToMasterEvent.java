package io.atomiclimes.client.events;

import org.springframework.context.ApplicationEvent;

import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;

public class ClientConnectedToMasterEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private AtomicLimesRegistrationResponse registrationResponse;

	public ClientConnectedToMasterEvent(Object source, AtomicLimesRegistrationResponse registrationResponse) {
		super(source);
		this.registrationResponse = registrationResponse;
	}

	public AtomicLimesRegistrationResponse getRegistrationResponse() {
		return registrationResponse;
	}

}
