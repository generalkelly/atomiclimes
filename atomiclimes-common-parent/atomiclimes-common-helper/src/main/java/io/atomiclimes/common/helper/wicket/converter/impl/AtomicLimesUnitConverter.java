package io.atomiclimes.common.helper.wicket.converter.impl;

import java.util.Locale;

import io.atomiclimes.common.helper.enums.PackagingUnit;
import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class AtomicLimesUnitConverter extends AtomicLimesConverter<PackagingUnit> {

	private static final long serialVersionUID = 1L;

	@Override
	public PackagingUnit convertToObject(String value, Locale locale) {
		PackagingUnit unit = null;
		PackagingUnit[] units = PackagingUnit.values();
		for (PackagingUnit u : units) {
			if (u.getUnit() == value) {
				unit = u;
			}
		}
		return unit;
	}

	@Override
	public String convertToString(PackagingUnit value, Locale locale) {
		return value.getUnit();
	}

}
