package io.atomiclimes.data.collector.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import io.atomiclimes.data.collector.configuration.AtomicLimesaDataCollectorAgentRegistry;

public class AtomicLimesaDataCollectorStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private AtomicLimesaDataCollectorAgentRegistry agentRegistry;

	public AtomicLimesaDataCollectorStartupListener(AtomicLimesaDataCollectorAgentRegistry agentRegistry) {
		this.agentRegistry= agentRegistry;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.agentRegistry.poll();
	}

}
