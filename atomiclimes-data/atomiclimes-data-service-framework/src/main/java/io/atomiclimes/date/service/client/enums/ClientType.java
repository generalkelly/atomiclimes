package io.atomiclimes.date.service.client.enums;

public enum ClientType {

	DEFAULT("DEFAULT"), AGENT("AGENT"), COLLECTOR("COLLECTOR");

	private String type;

	ClientType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}
