package io.atomiclimes.data.collector.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import io.atomiclimes.communication.KafkaConfiguration;

@ConfigurationProperties(prefix = "io.atomiclimes.data.collector")
public class AtomicLimesDataCollectorProperties {

	private String agentListUrl;
	private KafkaConfiguration kafkaConfiguration;
	private InfluxDBConfig influxDbConfig;

	public String getAgentListUrl() {
		return agentListUrl;
	}

	public void setAgentListUrl(String agentListUrl) {
		Assert.notNull(agentListUrl, "The property io.atomiclimes.data.collector.agent-list-url is missing!");
		this.agentListUrl = agentListUrl;
	}

	public KafkaConfiguration getKafkaConfiguration() {
		return kafkaConfiguration;
	}

	public void setKafkaConfiguration(KafkaConfiguration kafkaConfiguration) {
		Assert.notNull(kafkaConfiguration,
				"The property io.atomiclimes.data.collector.kafka-configuration is missing!");
		this.kafkaConfiguration = kafkaConfiguration;
	}

	public InfluxDBConfig getInfluxDbConfig() {
		return influxDbConfig;
	}

	public void setInfluxDbConfig(InfluxDBConfig influxDbConfig) {
		Assert.notNull(influxDbConfig, "The property io.atomiclimes.data.collector.influx-db-config is missing!");
		this.influxDbConfig = influxDbConfig;
	}

}
