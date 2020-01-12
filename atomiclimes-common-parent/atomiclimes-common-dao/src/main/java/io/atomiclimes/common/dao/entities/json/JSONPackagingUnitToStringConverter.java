package io.atomiclimes.common.dao.entities.json;

import com.fasterxml.jackson.databind.util.StdConverter;

import io.atomiclimes.common.helper.enums.PackagingUnit;

public class JSONPackagingUnitToStringConverter extends StdConverter<PackagingUnit, String> {

	@Override
	public String convert(PackagingUnit value) {
		return value.name();
	}

}
