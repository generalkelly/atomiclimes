package io.atomiclimes.data.collector.connections;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.kafka.KafkaConsumer;
import org.apache.edgent.connectors.kafka.KafkaConsumer.StringConsumerRecord;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;
import org.springframework.util.Assert;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.communication.KafkaConfiguration;

public class AtomicLimesKafkaConnector implements AtomicLimesConnector<KafkaConsumer> {

	private KafkaConfiguration kafkaConfiguration;
	private String sourceName;

	public AtomicLimesKafkaConnector(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
	public KafkaConsumer getConnection(Topology topology) {
		Assert.notNull(this.kafkaConfiguration, "KafkaConfiguration must not be null!");
		Map<String, Object> kafkaConfig = this.kafkaConfiguration.createKafkaConfiguration();
		
		return new KafkaConsumer(topology, () -> kafkaConfig);
	}

	@Override
	public void configure(Object configuration) {
		Assert.isInstanceOf(KafkaConfiguration.class, configuration);
		this.kafkaConfiguration = (KafkaConfiguration) configuration;
	}

	@Override
	public String getSourceName() {
		return this.sourceName;
	}

	@Override
	public String getDestinationName() {
		return null;
	}

	@Override
	public KafkaConsumer getConnection() {
		return null;
	}

}
