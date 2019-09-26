package io.atomiclimes.data.service.master.filters;

import java.util.Map.Entry;

import io.atomiclimes.date.service.client.enums.ClientType;

public class FilterEntry implements Entry<ClientType, AtomicLimesClientFilter> {

	private ClientType key;
	private AtomicLimesClientFilter filter;

	public FilterEntry(AtomicLimesClientFilter filter) {
		this.filter = setValue(filter);
	}

	@Override
	public ClientType getKey() {
		return this.key;
	}

	@Override
	public AtomicLimesClientFilter getValue() {
		return this.filter;
	}

	@Override
	public AtomicLimesClientFilter setValue(AtomicLimesClientFilter filter) {
		this.key = filter.getType();
		this.filter = filter;
		return this.filter;
	}

}
