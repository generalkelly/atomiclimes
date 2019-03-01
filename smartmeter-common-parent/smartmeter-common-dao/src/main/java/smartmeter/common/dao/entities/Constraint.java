package smartmeter.common.dao.entities;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "Contraints")
public class Constraint {

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "processDuration")
	private Duration processDuration;

	@ManyToMany(mappedBy = "constraints")
	private List<Product> products = new LinkedList<>();

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
