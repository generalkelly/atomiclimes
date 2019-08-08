package io.atomiclimes.master.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.master.logging.AtomicLimesMasterLogMessage;

public class AtomicLimesMasterStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesMasterStartupListener.class);

	private AtomicLimesAgentHealthMonitor healthMonitor;

	public AtomicLimesMasterStartupListener(AtomicLimesAgentHealthMonitor healthMonitor) {
		this.healthMonitor = healthMonitor;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOG.info(AtomicLimesMasterLogMessage.STARTED_AGENT_HEALTH_MONITOR);

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable healthCheck = () -> this.healthMonitor.checkHealth();
		executor.scheduleWithFixedDelay(healthCheck, 10, 15, TimeUnit.SECONDS);
	}

}
