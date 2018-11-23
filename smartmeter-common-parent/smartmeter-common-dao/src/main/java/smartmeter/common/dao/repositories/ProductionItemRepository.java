package smartmeter.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import smartmeter.common.dao.entities.ProductionItem;


@Repository
public interface ProductionItemRepository extends JpaRepository<ProductionItem, Long> {

}
