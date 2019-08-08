package io.atomiclimes.data.collector.connections;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.kafka.KafkaConsumer;
import org.apache.edgent.topology.Topology;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.springframework.util.Assert;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.collector.configuration.InfluxDBConfig;

public class AtomicLimesInfluxDBConnector implements AtomicLimesConnector<InfluxDB> {

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesInfluxDBConnector.class);

	private String influxDbUrl;
	private String influxDbUsername;
	private String influxDbPassword;
	private String influxDbDatabase;

	private String destinationName;

	public AtomicLimesInfluxDBConnector(String destinationName) {
		this.destinationName = destinationName;
	}

	@Override
	public InfluxDB getConnection() {
		Assert.notNull(influxDbUrl, "influxDbUrl must not be null!");
		Assert.notNull(influxDbDatabase, "influxDbDatabase must not be null!");
		InfluxDB influxDB = InfluxDBFactory.connect(this.influxDbUrl, this.influxDbUsername, this.influxDbPassword);
		Pong response = influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
			LOG.error("Error pinging server.");
			return null;
		}
		influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
		influxDB.setRetentionPolicy("autogen");
		influxDB.setDatabase(this.influxDbDatabase);
		return influxDB;
	}

	@Override
	public void configure(Object configuration) {
		Assert.isInstanceOf(InfluxDBConfig.class, configuration);
		InfluxDBConfig config = (InfluxDBConfig) configuration;
		this.influxDbUrl = config.getUrl();
		this.influxDbUsername = config.getUsername();
		this.influxDbPassword = config.getPassword();
		this.influxDbDatabase = config.getDatabase();
	}

	@Override
	public String getSourceName() {
		return null;
	}

	@Override
	public String getDestinationName() {
		return this.destinationName;
	}

	@Override
	public KafkaConsumer getConnection(Topology topology) {
		return null;
	}

}
