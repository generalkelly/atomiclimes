package io.atomiclimes.web.gui.log;

import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesGuiLogMessages implements AtomicLimesLogMessage {
	FAILED_TO_DESERIALIZE_JSON("Failed to deserialize message: {}")
	;

	private String message;

	AtomicLimesGuiLogMessages(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
