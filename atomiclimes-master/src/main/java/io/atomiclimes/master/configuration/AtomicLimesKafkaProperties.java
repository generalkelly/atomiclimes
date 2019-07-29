package io.atomiclimes.master.configuration;

import java.util.HashMap;
import java.util.Map;

public class AtomicLimesKafkaProperties {

	private String bootstrapServer;
	private String groupId;
	private boolean enableAutoCommit;
	private int autoCommitInterval;
	private String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
	private String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

	public String getBootstrapServer() {
		return bootstrapServer;
	}

	public void setBootstrapServer(String bootstrapServer) {
		this.bootstrapServer = bootstrapServer;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isEnableAutoCommit() {
		return enableAutoCommit;
	}

	public void setEnableAutoCommit(boolean enableAutoCommit) {
		this.enableAutoCommit = enableAutoCommit;
	}

	public int getAutoCommitInterval() {
		return autoCommitInterval;
	}

	public void setAutoCommitInterval(int autoCommitInterval) {
		this.autoCommitInterval = autoCommitInterval;
	}

	public String getKeyDeserializer() {
		return keyDeserializer;
	}

	public void setKeyDeserializer(String keyDeserializer) {
		this.keyDeserializer = keyDeserializer;
	}

	public String getValueDeserializer() {
		return valueDeserializer;
	}

	public void setValueDeserializer(String valueDeserializer) {
		this.valueDeserializer = valueDeserializer;
	}

	public Map<String, Object> createKafkaPropertiesMap() {
		Map<String, Object> kafkaPropertiesMap = new HashMap<>();
//		kafkaPropertiesMap.put("group.id", this.getGroupId());
//		kafkaPropertiesMap.put("enable.auto.commit", this.isEnableAutoCommit());
//		kafkaPropertiesMap.put("auto.commit.interval", this.getAutoCommitInterval());
//		kafkaPropertiesMap.put("key.deserializer", this.getKeyDeserializer());
//		kafkaPropertiesMap.put("value.deserializer", this.getValueDeserializer());
		return kafkaPropertiesMap;
	}

	public Map<String, Object> createKafkaPropertiesMapWithBootstrapServer() {
		Map<String, Object> kafkaPropertiesMapWithBootstrapServer = new HashMap<>();
		kafkaPropertiesMapWithBootstrapServer.put("bootstrap.servers", this.getBootstrapServer());
		kafkaPropertiesMapWithBootstrapServer.putAll(createKafkaPropertiesMap());
		return kafkaPropertiesMapWithBootstrapServer;

	}

}
