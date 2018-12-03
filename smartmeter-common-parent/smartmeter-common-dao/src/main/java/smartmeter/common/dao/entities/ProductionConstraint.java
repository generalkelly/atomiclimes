package smartmeter.common.dao.entities;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Production_Contraints")
public class ProductionConstraint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "processName")
	private String name;

	@Column(name = "processDuration")
	private Duration processDuration;

	@ManyToMany(mappedBy = "constraints")
	private List<ProductionItem> productionItems = new LinkedList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<ProductionItem> getProductionItems() {
		return productionItems;
	}

	public void setProductionItems(List<ProductionItem> productionItems) {
		this.productionItems = productionItems;
	}

}
