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

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "Product")
public class Product {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;

	@Id
	@Column(name = "productName")
	private String name;

	@ManyToMany
	@JoinTable(name = "Product_Constraints", joinColumns = {
			@JoinColumn(name = "productName", referencedColumnName = "productName") }, inverseJoinColumns = {
					@JoinColumn(name = "name", referencedColumnName = "name") })
	private List<Constraint> constraints = new LinkedList<>();

	@OneToMany(fetch = FetchType.LAZY)
	private List<ProductionItem> productionItems = new LinkedList<>();

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

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public List<ProductionItem> getProductionItems() {
		return productionItems;
	}

	public void setProductionItems(List<ProductionItem> productionItems) {
		this.productionItems = productionItems;
	}

}
