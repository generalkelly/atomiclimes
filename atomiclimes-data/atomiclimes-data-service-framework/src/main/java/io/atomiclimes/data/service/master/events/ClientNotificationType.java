package io.atomiclimes.data.service.master.events;

public enum ClientNotificationType {
	CLIENT_REGISTERED("CLIENT_REGISTERED"), CLIENT_DEREGISTERED("CLIENT_DEREGISTERED"), AGENT_DOWN("AGENT_DOWN"),
	AGENT_UP("AGENT_UP");

	private String type;

	ClientNotificationType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}
