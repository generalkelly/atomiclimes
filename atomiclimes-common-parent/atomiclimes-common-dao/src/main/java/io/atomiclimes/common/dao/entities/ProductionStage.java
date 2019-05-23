package io.atomiclimes.common.dao.entities;

import java.time.Duration;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.atomiclimes.common.dao.enums.ProductionStageType;
import smartmeter.common.helper.serializer.DurationSerializer;

public interface ProductionStage {

	public Long getId();
	
	public ProductionStageType getProductionStageType();

	public OffsetDateTime getPlannedProductionTime();

	public void setPlannedProductionTime(OffsetDateTime plannedProductionTime);

	public Duration getEstimatedProductionDuration();

	@JsonDeserialize(using = DurationSerializer.class)
	public void setEstimatedProductionDuration(Duration estimatedProductionDuration);

}
