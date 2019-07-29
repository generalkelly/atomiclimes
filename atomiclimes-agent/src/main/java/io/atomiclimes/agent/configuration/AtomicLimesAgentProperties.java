package io.atomiclimes.agent.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "io.atomiclimes.agent")
public class AtomicLimesAgentProperties {

	@Value(value = "${spring.application.name}")
	private String name;

	private String ip;

	@Value(value = "${server.port}")
	private int port;

	private String masterUrl;

	private S7Connection s7Connection = new S7Connection();

	@NotNull
	private int pollingInterval = 1000;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getMasterUrl() {
		return masterUrl;
	}

	public void setMasterUrl(String masterURL) {
		this.masterUrl = masterURL;
	}

	public S7Connection getS7Connection() {
		return s7Connection;
	}

	public void setS7Connection(S7Connection s7Connection) {
		this.s7Connection = s7Connection;
	}

	public int getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	interface SimpleConnection {
		public String getConnectionString();

		Map<String, String> getFieldMap();
	}

	public class S7Connection implements SimpleConnection {
		@NotNull
		private String rack;
		@NotNull
		private String slot;
		@NotNull
		private String url;
		@NotEmpty
		private Map<String, String> fieldMap = new HashMap<>();

		public String getRack() {
			return rack;
		}

		public void setRack(String rack) {
			this.rack = rack;
		}

		public String getSlot() {
			return slot;
		}

		public void setSlot(String slot) {
			this.slot = slot;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public Map<String, String> getFieldMap() {
			return fieldMap;
		}

		public void setFieldMap(Map<String, String> fieldMap) {
			this.fieldMap = fieldMap;
		}

		@Override
		public String getConnectionString() {
			StringBuilder sb = new StringBuilder("s7://");
			sb.append(getUrl());
			sb.append("/");
			sb.append(getRack());
			sb.append("/");
			sb.append(getSlot());
			return sb.toString();
		}
	}
}