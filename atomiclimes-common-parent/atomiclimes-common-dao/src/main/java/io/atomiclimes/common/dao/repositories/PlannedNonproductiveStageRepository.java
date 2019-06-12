package io.atomiclimes.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.PlannedNonproductiveStage;

@Repository
public interface PlannedNonproductiveStageRepository extends JpaRepository<PlannedNonproductiveStage, Long> {

}
