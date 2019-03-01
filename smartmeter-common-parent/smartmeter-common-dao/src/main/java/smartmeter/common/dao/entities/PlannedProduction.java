package smartmeter.common.dao.entities;

import java.time.Duration;
import java.time.OffsetDateTime;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import smartmeter.common.helper.serializer.DurationSerializer;

@Entity
@Table(name = "Planned_Productions")
public class PlannedProduction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productionItemId")
	private ProductionItem productionItem;

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

	public OffsetDateTime getPlannedProductionTime() {
		return plannedProductionTime;
	}

	public void setPlannedProductionTime(OffsetDateTime plannedProductionTime) {
		this.plannedProductionTime = plannedProductionTime;
	}

	public Duration getEstimatedProductionDuration() {
		return estimatedProductionDuration;
	}

	@JsonDeserialize(using = DurationSerializer.class)
	public void setEstimatedProductionDuration(Duration estimatedProductionDuration) {
		this.estimatedProductionDuration = estimatedProductionDuration;
	}

}
