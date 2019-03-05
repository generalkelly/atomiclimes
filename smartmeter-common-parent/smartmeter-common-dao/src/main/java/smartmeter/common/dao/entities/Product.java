package smartmeter.common.dao.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@Column(name = "productName")
	private String name;

	@OneToMany(fetch = FetchType.LAZY)
	private List<ProductionItem> productionItems = new LinkedList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductionItem> getProductionItems() {
		return productionItems;
	}

	public void setProductionItems(List<ProductionItem> productionItems) {
		this.productionItems = productionItems;
	}

}
