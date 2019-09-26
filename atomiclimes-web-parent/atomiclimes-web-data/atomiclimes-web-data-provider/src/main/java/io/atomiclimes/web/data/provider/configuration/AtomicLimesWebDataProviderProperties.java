package io.atomiclimes.web.data.provider.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "io.atomiclimes.web.data.provider")
public class AtomicLimesWebDataProviderProperties {

	private InfluxDBConfig influxDbConfig;

	public void setInfluxDbConfig(InfluxDBConfig influxDbConfig) {
		this.influxDbConfig = influxDbConfig;
	}

	public InfluxDBConfig getInfluxDbConfig() {
		return this.influxDbConfig;
	}

}
