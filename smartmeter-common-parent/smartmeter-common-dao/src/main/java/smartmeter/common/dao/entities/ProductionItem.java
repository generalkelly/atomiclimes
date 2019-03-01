package smartmeter.common.dao.entities;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Production_Items")
public class ProductionItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productName", referencedColumnName = "productName")
	private Product product;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Production_Item_Packaging", joinColumns = {
			@JoinColumn(name = "productionItemId") }, inverseJoinColumns = { @JoinColumn(name = "packagingId") })
	private List<Packaging> packaging;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productionItem")
	private List<PlannedProduction> plannedProductions;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<PlannedProduction> getPlannedProductions() {
		return plannedProductions;
	}

	public void setPlannedProductions(List<PlannedProduction> plannedProductions) {
		this.plannedProductions = plannedProductions;
	}

}
