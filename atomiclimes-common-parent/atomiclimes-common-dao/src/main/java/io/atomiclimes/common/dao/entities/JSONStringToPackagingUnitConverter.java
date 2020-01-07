package io.atomiclimes.common.dao.entities;

import com.fasterxml.jackson.databind.util.StdConverter;

import io.atomiclimes.common.helper.enums.PackagingUnit;

public class JSONStringToPackagingUnitConverter extends StdConverter<String, PackagingUnit> {

	@Override
	public PackagingUnit convert(String value) {
		return PackagingUnit.valueOf(value);
	}

}
