package io.atomiclimes.web.data.provider.connections;

import java.util.List;

import org.apache.edgent.function.Consumer;

public class InfluxDbSubscriber implements Consumer<Consumer<List<List<Object>>>>, AutoCloseable {

	private static final long serialVersionUID = 1L;
	private AtomicLimesInfluxDBConnector connector;
	private Consumer<List<List<Object>>> eventSubmitter;
	private String agentName;
	private String measurement;
	private String timeWindow;

	public InfluxDbSubscriber(AtomicLimesInfluxDBConnector connector, String agentName, String measurement,
			String timeWindow) {
		this.connector = connector;
		this.agentName = agentName;
		this.measurement = measurement;
		this.timeWindow = timeWindow;
	}

	@Override
	public void close() throws Exception {

	}

	@Override
	public void accept(Consumer<List<List<Object>>> eventSubmitter) {
		this.eventSubmitter = eventSubmitter;
		this.connector.start(this, this.agentName, this.measurement, this.timeWindow);
	}

	public void accept(List<List<Object>> rec) {
		try {
			eventSubmitter.accept(rec);
		} catch (Exception e) {
//			TODO: add logger
		}
	}

}
