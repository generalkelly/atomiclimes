package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Production_Items")
public class ProductionItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "productName", referencedColumnName = "productName")
	private Product product;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Production_Item_Packaging", joinColumns = {
			@JoinColumn(name = "productionItemId") }, inverseJoinColumns = { @JoinColumn(name = "packagingId") })
	private List<Packaging> packaging;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productionItem")
	private List<PlannedProduction> plannedProductions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Packaging> getPackaging() {
		return packaging;
	}

	public void setPackaging(List<Packaging> packaging) {
		this.packaging = packaging;
	}

	@JsonIgnore
	public List<PlannedProduction> getPlannedProductions() {
		return plannedProductions;
	}

	public void setPlannedProductions(List<PlannedProduction> plannedProductions) {
		this.plannedProductions = plannedProductions;
	}

	public Packaging getPackagingOfOrder(int order) {
		if (packaging != null && packaging.isEmpty() == false) {
			for (Packaging p : this.packaging) {
				if (p.getPackagingOrder() == order) {
					return p;
				}
			}
			return null;
		} else {
			return null;
		}
	}

}
