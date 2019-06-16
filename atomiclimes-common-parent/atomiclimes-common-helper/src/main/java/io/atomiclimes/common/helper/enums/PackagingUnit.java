package io.atomiclimes.common.helper.enums;

public enum PackagingUnit {

	LITERS("liters", "l"), HECTO_LITERS("hectoliters", "hl", LITERS, 100), UNITS("units", ""), GRAMS("grams", "g");

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public PackagingUnit getSiUnit() {
		return siUnit;
	}

	public double getFactor() {
		return factor;
	}

	private String name;
	private String unit;
	private PackagingUnit siUnit;
	private double factor;

	private PackagingUnit(String name, String unit) {
		this.name = name;
		this.unit = unit;
		this.siUnit = this;
		this.factor = 1;
	}

	private PackagingUnit(String name, String unit, PackagingUnit siUnit, double factor) {
		this.name = name;
		this.unit = unit;
		this.siUnit = siUnit;
		this.factor = factor;
	}

}
