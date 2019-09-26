package io.atomiclimes.data.service.master.configuration;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.atomiclimes.data.service.master.filters.AtomicLimesClientFilter;
import io.atomiclimes.data.service.master.filters.FilterEntry;
import io.atomiclimes.date.service.client.enums.ClientType;

public class AtomicLimesClientCollection {

	private Map<ClientType, AtomicLimesClientFilter> clientFilterMap;

	public AtomicLimesClientCollection(Set<FilterEntry> clientFilters) {
		this.setClientFilterMap(clientFilters);
	}

	public Map<ClientType, AtomicLimesClientFilter> getClientFilterMap() {
		return clientFilterMap;
	}

	public void setClientFilterMap(Set<FilterEntry> clientFilters) {
		this.clientFilterMap = clientFilters.stream()
				.collect(Collectors.toMap(FilterEntry::getKey, FilterEntry::getValue));
	}

}
