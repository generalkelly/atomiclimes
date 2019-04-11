package de.smartmeter.common.kafka.connector;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadRequest.Builder;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.smartmeter.common.kafka.connector.application.SmartMeterKafkaConnectorProperties;
import de.smartmeter.common.kafka.connector.util.S7Address;
import de.smartmeter.common.kafka.connector.util.S7Properties;

@Component
public class S7KafkaConnector implements CommandLineRunner {

	private SmartMeterKafkaConnectorProperties properties;
	private PlcConnection plcConnection;
	private Builder builder;

	public S7KafkaConnector(SmartMeterKafkaConnectorProperties properties) {
		this.properties = properties;
		initialize();
	}

	private void initialize() {
		try {
			this.plcConnection = new PlcDriverManager().getConnection(getConnectionUrl());
		} catch (PlcConnectionException e) {
			e.printStackTrace();
			return;
		}
		this.builder = plcConnection.readRequestBuilder();

		S7Properties s7Properties = this.properties.getS7Properties();

		Map<String, S7Address> s7Addresses = s7Properties.getAddresses();

		for (Entry<String, S7Address> entry : s7Addresses.entrySet()) {
			builder.addItem(entry.getKey(), entry.getValue().getAddress() + ":" + entry.getValue().getType());
		}
	}

	private String getConnectionUrl() {
		S7Properties s7Properties = this.properties.getS7Properties();
		return "s7://" + s7Properties.getUrl() + "/" + s7Properties.getRack() + "/" + s7Properties.getSlot();
	}

	private static void printResponse(PlcReadResponse response) {
		for (String fieldName : response.getFieldNames()) {
			if (response.getResponseCode(fieldName) == PlcResponseCode.OK) {
				int numValues = response.getNumberOfValues(fieldName);
				// If it's just one element, output just one single line.
				if (numValues == 1) {
					System.out.println("Value[" + fieldName + "]: " + response.getObject(fieldName));
				}
				// If it's more than one element, output each in a single row.
				else {
					System.out.println("Value[" + fieldName + "]:");
					for (int i = 0; i < numValues; i++) {
						System.out.println(" - " + response.getObject(fieldName, i));
					}
				}
			}
			// Something went wrong, to output an error message instead.
			else {
				System.out.println("Error[" + fieldName + "]: " + response.getResponseCode(fieldName).name());
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
//		if (args.length < 2) {
//			System.out.println("Usage: HelloPlc4x {connection-string} {address-string}+");
//			System.out.println("Example: HelloPlc4x s7://10.10.64.20/1/1 %Q0.0:BOOL %Q0:BYTE");
//			return;
//		}

		// Establish a connection to the plc using the url provided as first argument
//		try (PlcConnection plcConnection = new PlcDriverManager().getConnection(args[0])) {
//
//			// Check if this connection support reading of data.
//			if (!plcConnection.getMetadata().canRead()) {
//				System.err.println("This connection doesn't support reading.");
//				return;
//			}

		// Create a new read request:
		// - Give the single item requested the alias name "value"
//		PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
//		for (int i = 1; i < args.length; i++) {
//			builder.addItem("value-" + i, args[i]);
//		}

		//////////////////////////////////////////////////////////
		// Read synchronously ...
		// NOTICE: the ".get()" immediately lets this thread pause until
		// the response is processed and available.
		System.out.println("Synchronous request ...");
		PlcReadRequest syncReadRequest = builder.build();
		PlcReadResponse syncResponse = syncReadRequest.execute().get();
		// Simply iterating over the field names returned in the response.
		printResponse(syncResponse);

		//////////////////////////////////////////////////////////
		// Read asynchronously ...
		// Register a callback executed as soon as a response arrives.
		System.out.println("Asynchronous request ...");
		PlcReadRequest asyncReadRequest = builder.build();
		CompletableFuture<? extends PlcReadResponse> asyncResponse = asyncReadRequest.execute();
		asyncResponse.whenComplete((readResponse, throwable) -> {
			if (readResponse != null) {
				printResponse(readResponse);
			} else {
				System.err.println("An error occurred: " + throwable.getMessage());
				throwable.printStackTrace();
			}
		});
	}
}
