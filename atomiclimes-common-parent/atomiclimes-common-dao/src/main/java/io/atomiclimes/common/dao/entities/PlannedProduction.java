package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.atomiclimes.common.helper.enums.PackagingUnit;
import io.atomiclimes.common.helper.enums.ProductionStageType;
import io.atomiclimes.common.helper.serializer.DurationSerializer;

@Entity
@Table(name = "Planned_Productions")
public class PlannedProduction implements Serializable, ProductionStage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productionItemId")
	private ProductionItem productionItem;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plannedProduction")
	private List<PlannedNonproductiveStage> subsequentPlannedNonproductiveStages;

	@CreationTimestamp
	@Column(name = "creationTimestamp")
	private OffsetDateTime creationTimestamp;

	@UpdateTimestamp
	@Column(name = "updateTimestamp")
	private OffsetDateTime updateTimestamp;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "scheduledProductionDate")
	private LocalDate plannedProductionDate;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "scheduledProductionTime")
	private OffsetDateTime plannedProductionTime;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "unit")
	private PackagingUnit unit;

	@Column(name = "estimatedProductionDuration")
	private Duration estimatedProductionDuration;

	private ProductionStageType productionStageType = ProductionStageType.PRODUCTIVE;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductionItem getProductionItem() {
		return productionItem;
	}

	public void setProductionItem(ProductionItem productionItem) {
		this.productionItem = productionItem;
	}

	public List<PlannedNonproductiveStage> getSubsequentPlannedNonproductiveStages() {
		if (subsequentPlannedNonproductiveStages == null) {
			subsequentPlannedNonproductiveStages = new LinkedList<>();
		}
		return subsequentPlannedNonproductiveStages;
	}

	public void setSubsequentPlannedNonproductiveStages(
			List<PlannedNonproductiveStage> subsequentPlannedNonproductiveStages) {
		this.subsequentPlannedNonproductiveStages = subsequentPlannedNonproductiveStages;
	}

	public OffsetDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(OffsetDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public OffsetDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(OffsetDateTime updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public LocalDate getPlannedProductionDate() {
		return plannedProductionDate;
	}

	public void setPlannedProductionDate(LocalDate plannedProductionDate) {
		this.plannedProductionDate = plannedProductionDate;
	}

	@Override
	public OffsetDateTime getPlannedProductionTime() {
		return plannedProductionTime;
	}

	@Override
	public void setPlannedProductionTime(OffsetDateTime plannedProductionTime) {
		this.plannedProductionTime = plannedProductionTime;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public PackagingUnit getUnit() {
		return unit;
	}

	public void setUnit(PackagingUnit unit) {
		this.unit = unit;
	}

	public Duration getEstimatedProductionDuration() {
		if (estimatedProductionDuration == null) {
			new ProcessDurationCalculator().calculate(this);
		}
		return estimatedProductionDuration;
	}

	@JsonDeserialize(using = DurationSerializer.class)
	public void setEstimatedProductionDuration(Duration estimatedProductionDuration) {
		this.estimatedProductionDuration = estimatedProductionDuration;
	}

	@Override
	public ProductionStageType getProductionStageType() {
		return this.productionStageType;
	}

}
