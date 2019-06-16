package io.atomiclimes.common.dao.repositories;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.PlannedProduction;

@Repository
public interface PlannedProductionRepository extends JpaRepository<PlannedProduction, Long> {

	static final String FIND_SUBSEQUENT_PLANNED_PRODUCTION_QUERY = "SELECT p FROM PlannedProduction p WHERE p.plannedProductionTime > :plannedProductionTime and p.plannedProductionTime = (SELECT MIN(p.plannedProductionTime) FROM PlannedProduction p WHERE p.plannedProductionTime > :plannedProductionTime)";
	static final String FIND_PRECEEDING_PLANNED_PRODUCTION_QUERY = "SELECT p FROM PlannedProduction p WHERE p.plannedProductionTime < :plannedProductionTime and p.plannedProductionTime = (SELECT MAX(p.plannedProductionTime) FROM PlannedProduction p WHERE p.plannedProductionTime < :plannedProductionTime)";

	@Query(value = FIND_PRECEEDING_PLANNED_PRODUCTION_QUERY)
	Optional<PlannedProduction> findPreceedingPlannedProductionOf(
			@Param("plannedProductionTime") OffsetDateTime plannedProductionTime);

	@Query(value = FIND_SUBSEQUENT_PLANNED_PRODUCTION_QUERY)
	Optional<PlannedProduction> findSubsequentPlannedProductionOf(
			@Param("plannedProductionTime") OffsetDateTime plannedProductionTime);

}
