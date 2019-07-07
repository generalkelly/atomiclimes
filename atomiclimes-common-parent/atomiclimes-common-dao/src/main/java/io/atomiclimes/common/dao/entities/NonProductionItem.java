package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.atomiclimes.common.helper.annotations.AtomicLimesItemForm;
import io.atomiclimes.common.helper.annotations.AtomicLimesItemFormField;
import io.atomiclimes.common.helper.wicket.converter.impl.AtomicLimesDurationInSecondsConverter;

@Entity
@Table(name = "Non_Production_Items")
@AtomicLimesItemForm("Non Production Item Form")
public class NonProductionItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name")
	@AtomicLimesItemFormField(fieldName = "Non Production Item Name")
	private String name;

	@Column(name = "duration")
	@AtomicLimesItemFormField(fieldName = "Non Production Item Duration", using = AtomicLimesDurationInSecondsConverter.class)
	private Duration duration;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nonProductionItem")
	private List<PlannedNonproductiveStage> plannedNonProductiveStages;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	@JsonIgnore
	public List<PlannedNonproductiveStage> getPlannedProductions() {
		return plannedNonProductiveStages;
	}

	public void setPlannedProductions(List<PlannedNonproductiveStage> plannedNonProductiveStages) {
		this.plannedNonProductiveStages = plannedNonProductiveStages;
	}

	@Override
	public String toString() {
		return getName();
	}

}
