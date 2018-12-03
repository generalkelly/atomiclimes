package smartmeter.common.dao.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Production_Items")
public class ProductionItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Production_Item_Constraints", joinColumns = {
			@JoinColumn(name = "productionItemId") }, inverseJoinColumns = {
					@JoinColumn(name = "productionConstraintId") })
	private List<ProductionConstraint> constraints = new LinkedList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productionItem")
	private List<PlannedProduction> plannedProductions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ProductionConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<ProductionConstraint> constraints) {
		this.constraints = constraints;
	}

	public List<PlannedProduction> getPlannedProductions() {
		return plannedProductions;
	}

	public void setPlannedProductions(List<PlannedProduction> plannedProductions) {
		this.plannedProductions = plannedProductions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
