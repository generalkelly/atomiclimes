package io.atomiclimes.common.dao.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.atomiclimes.common.dao.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, Serializable {

	static final String DELETE_BY_NAME_QUERY = "DELETE FROM Product p WHERE p.name = :#{#product.name}";

	@Override
	@Transactional
	@Modifying
	@Query(DELETE_BY_NAME_QUERY)
	void delete(@Param("product") Product product);

}
