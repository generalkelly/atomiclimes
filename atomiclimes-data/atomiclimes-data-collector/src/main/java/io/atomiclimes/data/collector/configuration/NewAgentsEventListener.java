package io.atomiclimes.data.collector.configuration;

import org.springframework.context.ApplicationListener;

import io.atomiclimes.data.collector.listener.events.NewAgentsEvent;

public class NewAgentsEventListener implements ApplicationListener<NewAgentsEvent> {

	private AtomicLimesDataTransporterRegistry transporterRegistry;

	public NewAgentsEventListener(AtomicLimesDataTransporterRegistry transporterRegistry) {
		this.transporterRegistry = transporterRegistry;
	}

	@Override
	public void onApplicationEvent(NewAgentsEvent event) {
		transporterRegistry.addTransporters(event.getNewAgents());
	}

}
