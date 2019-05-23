package io.atomiclimes.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
