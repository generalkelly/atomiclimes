package smartmeter.common.dao.enums;

public enum PackagingUnit {

	LITERS("liters", "L"), UNITS("units", ""), GRAMS("grams", "g");

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	private String name;
	private String unit;

	private PackagingUnit(String name, String unit) {
		this.name = name;
		this.unit = unit;
	}

}
