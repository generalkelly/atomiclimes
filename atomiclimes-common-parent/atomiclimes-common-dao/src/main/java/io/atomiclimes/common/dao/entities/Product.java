package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@JsonIgnore
	public List<ProductionItem> getProductionItems() {
		return productionItems;
	}

	public void setProductionItems(List<ProductionItem> productionItems) {
		this.productionItems = productionItems;
	}

}
