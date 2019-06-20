package io.atomiclimes.common.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.atomiclimes.common.dao.entities.Packaging;

@Repository
public interface PackagingRepository extends CrudRepository<Packaging, Long> {

	static final String FIND_BY_NAME_QUERY = "SELECT p FROM Packaging p WHERE p.name = :name";

	@Query(FIND_BY_NAME_QUERY)
	Packaging findByName(@Param("name") String name);

}
