package io.atomiclimes.data.service.master.filters;

import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.data.service.dto.AtomicLimesRegistrationResponse;
import io.atomiclimes.date.service.client.enums.ClientType;

public interface AtomicLimesClientFilter {

	public AtomicLimesRegistrationResponse filter(AtomicLimesClient client);

	public ClientType getType();

}
