package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.atomiclimes.common.dao.entities.json.JSONDurationToLongConverter;
import io.atomiclimes.common.dao.entities.json.JSONLongToDurationConverter;
import io.atomiclimes.common.dao.entities.json.JSONPackagingUnitToStringConverter;
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
	@JsonSerialize(converter = JSONPackagingUnitToStringConverter.class)
	@JsonDeserialize(converter = JSONStringToPackagingUnitConverter.class)
	private PackagingUnit unit;

	@Column(name = "duration")
	@AtomicLimesItemFormField(fieldName = "Packaging Duration", using = AtomicLimesDurationInSecondsConverter.class)
	@JsonSerialize(converter = JSONDurationToLongConverter.class)
	@JsonDeserialize(converter = JSONLongToDurationConverter.class)
	private Duration duration;

	@Column(name = "packagingOrder")
	@AtomicLimesItemFormField(fieldName = "Packaging Order")
	private Integer packagingOrder;

	@ManyToMany(mappedBy = "packaging")
	@JsonIgnore
	private Set<ProductionItem> productionItems = new HashSet<>();

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

	public Integer getPackagingOrder() {
		return packagingOrder;
	}

	public void setPackagingOrder(Integer packagingOrder) {
		this.packagingOrder = packagingOrder;
	}

	@JsonIgnore
	public Set<ProductionItem> getProductionItems() {
		return productionItems;
	}

	public void setProductionItems(Set<ProductionItem> productionItems) {
		this.productionItems = productionItems;
	}

	public String toString() {
		return getName();
	}

}
