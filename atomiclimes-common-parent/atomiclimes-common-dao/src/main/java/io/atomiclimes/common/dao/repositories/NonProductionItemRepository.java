package io.atomiclimes.common.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.NonProductionItem;

@Repository
public interface NonProductionItemRepository extends CrudRepository<NonProductionItem, Long> {

	static final String FIND_BY_NAME_QUERY = "SELECT n FROM NonProductionItem n WHERE n.name = :name";

	@Query(value = FIND_BY_NAME_QUERY)
	NonProductionItem findByName(@Param("name") String preceedingPlannedNonProductiveStageType);

}
