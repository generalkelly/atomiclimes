import io.atomiclimes.common.logging.AtomicLimesLogMessage;

public enum AtomicLimesTestMessage implements AtomicLimesLogMessage {
	TEST("This is a message with three arguments {}, {}, {}");

	private String message;
	
	AtomicLimesTestMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
