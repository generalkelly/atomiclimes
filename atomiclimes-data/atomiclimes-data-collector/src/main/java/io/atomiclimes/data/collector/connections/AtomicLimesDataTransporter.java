package io.atomiclimes.data.collector.connections;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.kafka.KafkaConsumer;
import org.apache.edgent.connectors.kafka.KafkaConsumer.StringConsumerRecord;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.helper.jackson.AtomicLimesJacksonHelper;

public class AtomicLimesDataTransporter implements Runnable {

	private AtomicLimesKafkaConnector atomicLimesKafkaConnector;
	private AtomicLimesInfluxDBConnector atomicLimesInfluxDBConnector;
	private final AtomicLimesLogger LOG;

	public AtomicLimesDataTransporter(AtomicLimesKafkaConnector atomicLimesKafkaConnector,
			AtomicLimesInfluxDBConnector atomicLimesInfluxDBConnector, AtomicLimesLogger logger) {
		this.atomicLimesKafkaConnector = atomicLimesKafkaConnector;
		this.atomicLimesInfluxDBConnector = atomicLimesInfluxDBConnector;
		this.LOG = logger;
	}

	@Override
	public void run() {
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology("kafka-influxdb-bridge");
		KafkaConsumer kafkaConsumer = atomicLimesKafkaConnector.getConnection(topology);
		InfluxDB influxDB = atomicLimesInfluxDBConnector.getConnection();

		LOG.info("Subscribing to Kafka topic " + atomicLimesKafkaConnector.getSourceName());
		TStream<String> jsonDataStream = kafkaConsumer.subscribe(StringConsumerRecord::value,
				atomicLimesKafkaConnector.getSourceName());
		
		jsonDataStream.sink(jsonString -> {
			LOG.info("writing");
			Map<String, Object> fieldsToMap = generateFieldsToAddFromJson(jsonString);
			influxDB.write(this.generatePoint(atomicLimesInfluxDBConnector.getDestinationName(), fieldsToMap));
		});

		dp.submit(topology);
		
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> generateFieldsToAddFromJson(String jsonString) {
		AtomicLimesJacksonHelper helper = new AtomicLimesJacksonHelper(Map.class);
		return (Map<String, Object>) helper.deserialize(jsonString);
	}

	private Point generatePoint(String measurement, Map<String, Object> fieldsToAdd) {
		Builder pointBuilder = Point.measurement(measurement);
		pointBuilder.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		pointBuilder.fields(fieldsToAdd);
		return pointBuilder.build();
	}

}
