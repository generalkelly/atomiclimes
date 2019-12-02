package io.atomiclimes.common.dao.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.atomiclimes.common.helper.annotations.AtomicLimesItemForm;
import io.atomiclimes.common.helper.annotations.AtomicLimesItemFormField;
import io.atomiclimes.common.helper.enums.AtomicLimesFormInputType;

@Entity
@Table(name = "Production_Items")
@AtomicLimesItemForm(value = "Production Item Form")
public class ProductionItem implements Serializable {

	private static final String SEMICOLON_DELIMITER = "; ";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "productName", referencedColumnName = "productName")
	@AtomicLimesItemFormField(fieldName = "Product", fieldType = AtomicLimesFormInputType.DROPDOWN_CHOICE)
	private Product product;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "Production_Item_Packaging", joinColumns = {
			@JoinColumn(name = "productionItemId") }, inverseJoinColumns = { @JoinColumn(name = "packagingId") })
	@AtomicLimesItemFormField(fieldName = "Packaging", fieldType = AtomicLimesFormInputType.MULTIPLE_CHOICE)
	private Set<Packaging> packaging;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productionItem")
	private Set<PlannedProduction> plannedProductions;

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

	public Set<Packaging> getPackaging() {
		return packaging;
	}

	public void setPackaging(Set<Packaging> packaging) {
		this.packaging = packaging;
	}

	@JsonIgnore
	public Set<PlannedProduction> getPlannedProductions() {
		return plannedProductions;
	}

	public void setPlannedProductions(Set<PlannedProduction> plannedProductions) {
		this.plannedProductions = plannedProductions;
	}

	public Packaging getPackagingOfOrder(int order) {
		if (packaging != null && !packaging.isEmpty()) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.product != null) {
			sb.append(this.product.getName());
		}
		if (packaging != null && !packaging.isEmpty()) {
			packaging.stream().forEach(pack -> {
				sb.append(SEMICOLON_DELIMITER);
				sb.append(pack.getName());
			});
		}
		return sb.toString();
	}

}
