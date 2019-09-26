package io.atomiclimes.web.data.provider.connections;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.atomiclimes.common.logging.AtomicLimesLogger;

public class AtomicLimesRestTransporter implements Runnable {

	private AtomicLimesInfluxDBConnector atomicLimesInfluxDBConnector;
	private SseEmitter emitter;
	private String agentName;
	private String measurement;
	private String timeWindow;
	private boolean emitterCompleted = false;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesRestTransporter.class);

	public AtomicLimesRestTransporter(AtomicLimesInfluxDBConnector atomicLimesInfluxDBConnector, SseEmitter emitter,
			String agentName, String measurement, String timeWindow) {
		this.atomicLimesInfluxDBConnector = atomicLimesInfluxDBConnector;
		this.emitter = emitter;
		this.agentName = agentName;
		this.measurement = measurement;
		this.timeWindow = timeWindow;
	}

	@Override
	public void run() {
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology("influxdb-rest-bridge");

		InfluxDBConsumer influxDbConsumer = new InfluxDBConsumer(topology, atomicLimesInfluxDBConnector, agentName,
				measurement, timeWindow);
		TStream<List<List<Object>>> influxContentStream = influxDbConsumer.subscribe();

		influxContentStream.sink(entry -> {
			Runnable stopExecution = () -> {
				this.emitterCompleted();
				this.atomicLimesInfluxDBConnector.stop();
			};
			emitter.onCompletion(stopExecution);
			Consumer<Throwable> callback = (trhowable) -> {
				stopExecution.run();
			};
			emitter.onError(callback);
			try {
				if (!this.emitterCompleted) {
					emitter.send(entry);
				}
			} catch (IOException e) {
//				TODO: Add LogMessage
				LOG.error("Bad things happened, please add a LogMessage");
			}
		});

		dp.submit(topology);

	}

	private void emitterCompleted() {
		this.emitterCompleted = true;
	}
}
