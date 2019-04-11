package smartmeter.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import smartmeter.common.dao.entities.PlannedProduction;


@Repository
public interface PlannedProductionRepository extends JpaRepository<PlannedProduction, Long> {

}
