package io.atomiclimes.web.data.provider.connections;

public interface AtomicLimesConnector<C> {

	public C getConnection();

	public void configure(Object configuration);

	public String getSourceName();

	public String getDestinationName();


}
