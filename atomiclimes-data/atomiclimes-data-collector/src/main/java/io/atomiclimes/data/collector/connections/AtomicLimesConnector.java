package io.atomiclimes.data.collector.connections;

import org.apache.edgent.connectors.kafka.KafkaConsumer;
import org.apache.edgent.topology.Topology;

public interface AtomicLimesConnector<C> {

	public C getConnection();

	public void configure(Object configuration);

	public String getSourceName();

	public String getDestinationName();

	KafkaConsumer getConnection(Topology topology);

}
