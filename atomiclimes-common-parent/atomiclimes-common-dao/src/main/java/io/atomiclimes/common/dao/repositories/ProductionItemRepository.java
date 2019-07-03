package io.atomiclimes.common.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.ProductionItem;


@Repository
public interface ProductionItemRepository extends CrudRepository<ProductionItem, Long> {

}
