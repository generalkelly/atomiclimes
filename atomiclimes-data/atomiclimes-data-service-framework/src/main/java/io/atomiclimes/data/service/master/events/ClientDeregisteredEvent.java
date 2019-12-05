package io.atomiclimes.data.service.master.events;

import org.springframework.context.ApplicationEvent;

public class ClientDeregisteredEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ClientDeregisteredEvent(Object source) {
		super(source);
	}

}
