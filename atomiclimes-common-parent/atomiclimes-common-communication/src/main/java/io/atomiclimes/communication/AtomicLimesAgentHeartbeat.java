package io.atomiclimes.communication;

public class AtomicLimesAgentHeartbeat {

	String name;

	public AtomicLimesAgentHeartbeat() {
	}

	public AtomicLimesAgentHeartbeat(AtomicLimesAgent agent) {
		this.name = agent.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
