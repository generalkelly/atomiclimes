package smartmeter.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import smartmeter.common.dao.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
