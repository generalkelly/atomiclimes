package io.atomiclimes.data.collector.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.collector.connections.AtomicLimesDataTransporter;
import io.atomiclimes.data.collector.connections.AtomicLimesInfluxDBConnector;
import io.atomiclimes.data.collector.connections.AtomicLimesKafkaConnector;
import io.atomiclimes.data.collector.logging.AtomicLimesDataCollectorLogMessage;
import io.atomiclimes.data.service.dto.AtomicLimesClient;

public class AtomicLimesDataTransporterRegistry {

	Map<String, Future<?>> registryMap = new HashMap<>();
	private AtomicLimesDataCollectorProperties properties;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesDataTransporterRegistry.class);

	public AtomicLimesDataTransporterRegistry(AtomicLimesDataCollectorProperties properties) {
		this.properties = properties;
	}

	public void addTransporters(Map<String, AtomicLimesClient> newAgents) {
		Map<String, Future<?>> transporterFuturesMap = newAgents.entrySet().stream().collect(Collectors
				.toMap(Map.Entry::getKey, entry -> this.executorService.submit(generateTransporter(entry.getKey()))));
		registryMap.putAll(transporterFuturesMap);

		registryMap.entrySet().parallelStream().forEach(entry -> {
			try {
				entry.getValue().get();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				LOG.info(AtomicLimesDataCollectorLogMessage.TRANSPORTER_ERROR_LOG_MESSAGE);
				LOG.debug(AtomicLimesDataCollectorLogMessage.TRANSPORTER_ERROR_LOG_MESSAGE, e);
			}
		});

	}

	private AtomicLimesDataTransporter generateTransporter(String upperCaseAgentName) {
		AtomicLimesKafkaConnector atomicLimesKafkaConnector = new AtomicLimesKafkaConnector(upperCaseAgentName);
		atomicLimesKafkaConnector.configure(this.properties.getKafkaConfiguration());
		AtomicLimesInfluxDBConnector atomicLimesInfluxDBConnector = new AtomicLimesInfluxDBConnector(
				upperCaseAgentName);
		atomicLimesInfluxDBConnector.configure(this.properties.getInfluxDbConfig());
		LOG.info(AtomicLimesDataCollectorLogMessages.CREATED_TRANSPORTER_LOG_MESSAGE, upperCaseAgentName);
		return new AtomicLimesDataTransporter(atomicLimesKafkaConnector, atomicLimesInfluxDBConnector, LOG);
	}

	public boolean killTransporter(String upperCaseAgentName) {
		return this.registryMap.get(upperCaseAgentName).cancel(true);
	}

}
