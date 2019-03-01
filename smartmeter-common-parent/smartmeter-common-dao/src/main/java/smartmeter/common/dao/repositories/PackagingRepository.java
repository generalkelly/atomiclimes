package smartmeter.common.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import smartmeter.common.dao.entities.Packaging;

@Repository
public interface PackagingRepository extends JpaRepository<Packaging, Long> {

}
