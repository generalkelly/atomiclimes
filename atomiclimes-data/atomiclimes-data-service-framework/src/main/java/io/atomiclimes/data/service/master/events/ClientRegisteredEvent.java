package io.atomiclimes.data.service.master.events;

import org.springframework.context.ApplicationEvent;

public class ClientRegisteredEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ClientRegisteredEvent(Object source) {
		super(source);
	}

}
