package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.atomiclimes.common.helper.annotations.AtomicLimesItemForm;
import io.atomiclimes.common.helper.annotations.AtomicLimesItemFormField;
import io.atomiclimes.common.helper.enums.AtomicLimesFormInputType;
import io.atomiclimes.common.helper.enums.PackagingUnit;
import io.atomiclimes.common.helper.wicket.converter.impl.AtomicLimesDurationInSecondsConverter;
import io.atomiclimes.common.helper.wicket.converter.impl.AtomicLimesUnitConverter;

@Entity
@Table(name = "Packaging")
@AtomicLimesItemForm("Packaging Form")
public class Packaging implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "type")
	@AtomicLimesItemFormField(fieldName = "Packaging Type")
	private String name;

	@Column(name = "capacity")
	@AtomicLimesItemFormField(fieldName = "Capacity")
	private Double capacity;

	@Enumerated(EnumType.STRING)
	@Column(name = "unit")
	@AtomicLimesItemFormField(fieldName = "Unit", using = AtomicLimesUnitConverter.class, fieldType = AtomicLimesFormInputType.DROPDOWN_CHOICE)
	private PackagingUnit unit;

	@Column(name = "duration")
	@AtomicLimesItemFormField(fieldName = "Packaging Duration", using = AtomicLimesDurationInSecondsConverter.class)
	private Duration duration;

	@Column(name = "packagingOrder")
	@AtomicLimesItemFormField(fieldName = "Packaging Order")
	private Integer packagingOrder;

	@ManyToMany(mappedBy = "packaging")
	private List<ProductionItem> productionItems = new LinkedList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public PackagingUnit getUnit() {
		return unit;
	}

	public void setUnit(PackagingUnit unit) {
		this.unit = unit;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	@JsonIgnore
	public Integer getPackagingOrder() {
		return packagingOrder;
	}

	public void setPackagingOrder(Integer packagingOrder) {
		this.packagingOrder = packagingOrder;
	}

	@JsonIgnore
	public List<ProductionItem> getProductionItems() {
		return productionItems;
	}

	public void setProductionItems(List<ProductionItem> productionItems) {
		this.productionItems = productionItems;
	}

}
