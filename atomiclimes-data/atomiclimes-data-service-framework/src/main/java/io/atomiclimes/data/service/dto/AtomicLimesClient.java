package io.atomiclimes.data.service.dto;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.atomiclimes.date.service.client.enums.ClientType;

public class AtomicLimesClient {

	private String name;
	private ClientType type;
	private String ip;
	private int port;
	private OffsetDateTime upTime;
	private OffsetDateTime lastKeepAlive;
	private boolean isAlive;
	private UUID uuid = UUID.randomUUID();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String string) {
		this.ip = string;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public OffsetDateTime getUpTime() {
		return upTime;
	}

	public void setUpTime(OffsetDateTime upTime) {
		this.upTime = upTime;
	}

	public OffsetDateTime getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(OffsetDateTime lastKeepAlive) {
		this.lastKeepAlive = lastKeepAlive;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}


}
