package smartmeter.common.dao.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import smartmeter.common.dao.enums.PackagingUnit;

@Entity
@Table(name = "Packaging")
public class Packaging implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "type")
	private String name;

	@Column(name = "capacity")
	private double capacity;

	@Enumerated(EnumType.STRING)
	@Column(name = "unit")
	private PackagingUnit unit;

	@Column(name = "packagingOrder")
	private Integer packagingOrder;
	
	@ManyToMany(mappedBy = "packaging")
	private List<ProductionItem> productionItems = new LinkedList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public PackagingUnit getUnit() {
		return unit;
	}

	public void setUnit(PackagingUnit unit) {
		this.unit = unit;
	}

	public Integer getPackagingOrder() {
		return packagingOrder;
	}

	public void setPackagingOrder(Integer packagingOrder) {
		this.packagingOrder = packagingOrder;
	}

}
