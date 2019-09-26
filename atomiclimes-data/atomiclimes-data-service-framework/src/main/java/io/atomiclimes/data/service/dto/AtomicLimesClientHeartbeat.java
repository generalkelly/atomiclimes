package io.atomiclimes.data.service.dto;

public class AtomicLimesClientHeartbeat {

	String name;

	public AtomicLimesClientHeartbeat() {
	}

	public AtomicLimesClientHeartbeat(AtomicLimesClient client) {
		this.name = client.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
