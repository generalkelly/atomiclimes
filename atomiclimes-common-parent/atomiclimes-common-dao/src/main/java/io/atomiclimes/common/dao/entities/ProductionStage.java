package io.atomiclimes.common.dao.entities;

import java.time.Duration;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.atomiclimes.common.dao.entities.json.JSONDurationToLongConverter;
import io.atomiclimes.common.dao.entities.json.JSONLongToDurationConverter;
import io.atomiclimes.common.helper.enums.ProductionStageType;
import io.atomiclimes.common.helper.serializer.DurationSerializer;

public interface ProductionStage {

	public Long getId();
	
	public ProductionStageType getProductionStageType();

	public OffsetDateTime getPlannedProductionTime();

	public void setPlannedProductionTime(OffsetDateTime plannedProductionTime);

	@JsonSerialize(converter = JSONDurationToLongConverter.class)
	@JsonDeserialize(converter = JSONLongToDurationConverter.class)
	public Duration getEstimatedProductionDuration();

	@JsonDeserialize(using = DurationSerializer.class)
	public void setEstimatedProductionDuration(Duration estimatedProductionDuration);

}
