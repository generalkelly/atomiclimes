package smartmeter.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import smartmeter.common.dao.entities.Constraint;


@Repository
public interface ProductionConstraintRepository extends JpaRepository<Constraint, Long> {
	
}
