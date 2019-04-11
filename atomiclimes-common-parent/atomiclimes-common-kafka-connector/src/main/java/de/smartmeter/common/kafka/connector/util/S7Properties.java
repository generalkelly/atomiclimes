package de.smartmeter.common.kafka.connector.util;

import java.util.Map;

public class S7Properties {

	private String url;
	private String rack;
	private String slot;

	private Map<String, S7Address> addresses;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public Map<String, S7Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Map<String, S7Address> addresses) {
		this.addresses = addresses;
	}

}
