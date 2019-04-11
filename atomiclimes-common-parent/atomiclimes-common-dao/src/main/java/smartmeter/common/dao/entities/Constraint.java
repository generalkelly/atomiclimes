package smartmeter.common.dao.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Contraints")
public class Constraint implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "processDuration")
	private Duration processDuration;

	@ManyToMany(mappedBy = "constraints")
	private List<PlannedProduction> plannedProductions = new LinkedList<>();

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Duration getProcessDuration() {
		return processDuration;
	}

	public void setProcessDuration(Duration processDuration) {
		this.processDuration = processDuration;
	}

	public List<PlannedProduction> getPlannedProductions() {
		return plannedProductions;
	}

	public void setPlannedProductions(List<PlannedProduction> plannedProductions) {
		this.plannedProductions = plannedProductions;
	}

}
