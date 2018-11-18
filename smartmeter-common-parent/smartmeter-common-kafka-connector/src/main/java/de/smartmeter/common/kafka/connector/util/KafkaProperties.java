package de.smartmeter.common.kafka.connector.util;

import java.util.Map;

public class KafkaProperties {

	private String url;
	private Map<String, String> dataKafkaTopicMap;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getDataKafkaTopicMap() {
		return dataKafkaTopicMap;
	}

	public void setDataKafkaTopicMap(Map<String, String> dataKafkaTopicMap) {
		this.dataKafkaTopicMap = dataKafkaTopicMap;
	}

}
