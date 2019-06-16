package io.atomiclimes.common.helper.wicket.converter;

import org.apache.wicket.util.convert.IConverter;

public abstract class AtomicLimesConverter<C> implements IConverter<C> {

	private static final long serialVersionUID = 1L;

	public abstract static class None extends AtomicLimesConverter<Object> {
		private static final long serialVersionUID = 1L;

		private None() {
		}
	}

}
