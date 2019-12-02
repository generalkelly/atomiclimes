package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.atomiclimes.common.helper.enums.ProductionStageType;


@Entity
@Table(name = "Planned_Nonproductive_Stage")
public class PlannedNonproductiveStage implements Serializable, ProductionStage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nonProductionItemId")
	private NonProductionItem nonProductionItem;

	@ManyToOne
	@JoinColumn(name = "plannedProductionId")
	private PlannedProduction plannedProduction;

	@CreationTimestamp
	@Column(name = "creationTimestamp")
	private OffsetDateTime creationTimestamp;

	@UpdateTimestamp
	@Column(name = "updateTimestamp")
	private OffsetDateTime updateTimestamp;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "scheduledProductionTime")
	private OffsetDateTime plannedProductionTime;

	@Column(name = "estimatedProductionDuration")
	private Duration estimatedProductionDuration;

	private ProductionStageType productionStageType = ProductionStageType.NON_PRODUCTIVE;

	@Override
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public NonProductionItem getNonProductionItem() {
		return nonProductionItem;
	}

	public void setNonProductionItem(NonProductionItem nonProductionItem) {
		this.nonProductionItem = nonProductionItem;
	}

	public PlannedProduction getPlannedProduction() {
		return plannedProduction;
	}

	public void setPlannedProduction(PlannedProduction plannedProduction) {
		this.plannedProduction = plannedProduction;
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

	@Override
	public OffsetDateTime getPlannedProductionTime() {
		return plannedProductionTime;
	}

	@Override
	public void setPlannedProductionTime(OffsetDateTime plannedProductionTime) {
		this.plannedProductionTime = plannedProductionTime;
	}

	@Override
	public Duration getEstimatedProductionDuration() {
		if (estimatedProductionDuration == null) {
			new ProcessDurationCalculator().calculate(this);
		}
		return estimatedProductionDuration;
	}

	@Override
	public void setEstimatedProductionDuration(Duration estimatedProductionDuration) {
		this.estimatedProductionDuration = estimatedProductionDuration;
	}

	@Override
	public ProductionStageType getProductionStageType() {
		return this.productionStageType;
	}

}
