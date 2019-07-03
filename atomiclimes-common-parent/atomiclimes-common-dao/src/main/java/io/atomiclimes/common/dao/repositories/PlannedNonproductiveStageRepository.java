package io.atomiclimes.common.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.PlannedNonproductiveStage;

@Repository
public interface PlannedNonproductiveStageRepository extends CrudRepository<PlannedNonproductiveStage, Long> {

}
