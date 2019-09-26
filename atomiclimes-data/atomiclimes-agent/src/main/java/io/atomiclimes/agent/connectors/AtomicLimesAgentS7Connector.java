package io.atomiclimes.agent.connectors;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.kafka.KafkaProducer;
import org.apache.edgent.function.Supplier;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.plc4x.edgent.PlcConnectionAdapter;
import org.apache.plc4x.edgent.PlcFunctions;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.exceptions.PlcException;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadRequest.Builder;
import org.apache.plc4x.java.api.messages.PlcReadResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.atomiclimes.agent.configuration.AtomicLimesAgentProperties;
import io.atomiclimes.agent.configuration.AtomicLimesAgentProperties.S7Connection;
import io.atomiclimes.client.connectors.AtomicLimesClientConnector;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.netty.util.IllegalReferenceCountException;

public class AtomicLimesAgentS7Connector implements AtomicLimesClientConnector {

	private PlcConnection plcConnection;
	private AtomicLimesAgentProperties properties;
	private PlcConnectionAdapter plcConnectionAdapter;
	private Builder builder;
	private PlcReadRequest readRequest;
	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesAgentS7Connector.class);

	public AtomicLimesAgentS7Connector(AtomicLimesAgentProperties properties) {
		this.properties = properties;
	}

	@Override
	public void connect() {
		S7Connection s7Connection = this.properties.getS7Connection();
		try {
			this.plcConnection = new PlcDriverManager().getConnection(s7Connection.getConnectionString());
			LOG.info(AtomicLimesAgentMessages.CONNECTED_TO_PLC_LOG_MESSAGE,
					properties.getS7Connection().getConnectionString());
		} catch (PlcConnectionException | IllegalReferenceCountException e1) {
			LOG.info(AtomicLimesAgentMessages.COULD_NOT_CONNECT, s7Connection.getUrl(), s7Connection.getRack(),
					s7Connection.getSlot());
			LOG.debug(AtomicLimesAgentMessages.COULD_NOT_CONNECT, e1, s7Connection.getUrl(), s7Connection.getRack(),
					s7Connection.getSlot());
			try {
				TimeUnit.SECONDS.sleep(5);
				this.connect();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		this.plcConnectionAdapter = new PlcConnectionAdapter(plcConnection);
		try {
			this.builder = plcConnectionAdapter.readRequestBuilder();
		} catch (PlcException e) {
			LOG.error(AtomicLimesAgentMessages.PLC_EXCEPTION_LOG_MESSAGE, e);
		}
		addFieldMapItemsToBuilder(builder, this.properties.getS7Connection().getFieldMap());
		this.readRequest = builder.build();
	}

	@Override
	public void read(AtomicLimesRegistrationResponse registrationResponse) {

		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology("kafka-bridge");
		Supplier<PlcReadResponse> supplier = () -> {
			if (!plcConnection.isConnected()) {
				this.connect();
			}
			Supplier<PlcReadResponse> batchSupplier = PlcFunctions.batchSupplier(plcConnectionAdapter, readRequest);
			return batchSupplier.get();
		};

		TStream<PlcReadResponse> source = topology.poll(supplier, this.properties.getPollingInterval(),
				TimeUnit.MILLISECONDS);

		TStream<String> jsonString = source.map(this::createJsonStringFromPlcResponseValues);

		Map<String, Object> kafkaConfiguration = registrationResponse.getKafkaConfiguration();
		KafkaProducer kafkaProducer = new KafkaProducer(topology, () -> kafkaConfiguration);
		kafkaProducer.publish(jsonString, kafkaConfiguration.get("topic.name").toString());

		dp.submit(topology);
	}

	private String createJsonStringFromPlcResponseValues(PlcReadResponse value) {
		JsonObject jsonObject = new JsonObject();
		value.getFieldNames().forEach(fieldName -> {
			if (value.getNumberOfValues(fieldName) == 1) {
				jsonObject.addProperty(fieldName, value.getObject(fieldName).toString());
			} else if (value.getNumberOfValues(fieldName) > 1) {
				JsonArray values = new JsonArray();
				value.getAllBytes(fieldName).forEach(values::add);
				jsonObject.add(fieldName, values);
			}
		});
		LOG.info("Created JsonString from Plc value.");
		return jsonObject.toString();
	}

	private void addFieldMapItemsToBuilder(Builder builder, Map<String, String> fieldMap) {
		fieldMap.entrySet().stream().forEach(entry -> builder.addItem(entry.getKey(), entry.getValue()));
	}

}
