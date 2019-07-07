package io.atomiclimes.common.dao.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, Serializable {

	static final String DELETE_BY_NAME_QUERY = "DELETE FROM Product p WHERE p.name = :#{#product.name}";
	static final String FIND_BY_NAME_QUERY = "SELECT p FROM Product p WHERE p.name = :name";
	
	@Override
	@Transactional
	@Modifying
	@Query(DELETE_BY_NAME_QUERY)
	void delete(@Param("product") Product product);
	

	@Query(FIND_BY_NAME_QUERY)
	Product findByName(@Param("name") String name);

}
