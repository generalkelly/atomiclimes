package io.atomiclimes.web.data.provider.connections;

import java.util.List;

import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

public class InfluxDBConsumer {

	private Topology topology;
	private AtomicLimesInfluxDBConnector connector;
	private String agentName;
	private String measurement;
	private String timeWindow;

	public InfluxDBConsumer(Topology topology, AtomicLimesInfluxDBConnector connector, String agentName,
			String measurement, String timeWindow) {
		this.topology = topology;
		this.connector = connector;
		this.agentName = agentName;
		this.measurement = measurement;
		this.timeWindow = timeWindow;
	}

	public TStream<List<List<Object>>> subscribe() {
		return this.topology.events(new InfluxDbSubscriber(this.connector, this.agentName, this.measurement, this.timeWindow));
	}

}