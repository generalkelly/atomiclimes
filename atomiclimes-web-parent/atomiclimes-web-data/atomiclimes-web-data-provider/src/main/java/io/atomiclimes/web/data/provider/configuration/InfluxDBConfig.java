package io.atomiclimes.web.data.provider.configuration;

import javax.validation.constraints.NotBlank;

import org.springframework.util.Assert;

public class InfluxDBConfig {

	@NotBlank
	private String url;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String database;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		Assert.notNull(url, "The property io.atomiclimes.data.collector.influx-db-config.url is missing!");
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		Assert.notNull(database, "The property io.atomiclimes.data.collector.influx-db-config.database is missing!");
		this.database = database;
	}

}
