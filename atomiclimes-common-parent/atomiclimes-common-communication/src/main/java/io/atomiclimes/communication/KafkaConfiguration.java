package io.atomiclimes.communication;

import java.util.HashMap;
import java.util.Map;

public class KafkaConfiguration {

	private String bootstrapServer;
	private String zookeeperConnect;
	private String groupId;
	private String topicName;
	private Map<String, Object> kafkaProperties = new HashMap<>();

	public KafkaConfiguration() {

	}

	public KafkaConfiguration(KafkaConfiguration kafkaConfiguration) {
		this.bootstrapServer = kafkaConfiguration.getBootstrapServer();
		this.topicName = kafkaConfiguration.getTopicName();
		this.kafkaProperties = kafkaConfiguration.getKafkaProperties();
	}

	public String getBootstrapServer() {
		return bootstrapServer;
	}

	public void setBootstrapServer(String bootstrapServer) {
		this.bootstrapServer = bootstrapServer;
	}

	public String getZookeeperConnect() {
		return zookeeperConnect;
	}

	public void setZookeeperConnect(String zookeeperConnect) {
		this.zookeeperConnect = zookeeperConnect;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Map<String, Object> getKafkaProperties() {
		return kafkaProperties;
	}

	public void setKafkaProperties(Map<String, Object> kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	public Map<String, Object> createKafkaConfiguration() {
		Map<String, Object> kafkaConfigurationMap = new HashMap<>();
		kafkaConfigurationMap.put("bootstrap.servers", getBootstrapServer());
		kafkaConfigurationMap.put("zookeeper.connect", getZookeeperConnect());
		kafkaConfigurationMap.put("group.id", getGroupId());
		kafkaConfigurationMap.put("client.id", "bla");
		kafkaConfigurationMap.putAll(this.kafkaProperties);
		return kafkaConfigurationMap;
	}


}
