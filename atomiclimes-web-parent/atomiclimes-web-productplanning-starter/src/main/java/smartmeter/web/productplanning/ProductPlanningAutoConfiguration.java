package smartmeter.web.productplanning;

import javax.persistence.EntityManager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;
import io.atomiclimes.common.logic.AtomicLimesProductionPlanningCalculation;

@Configuration
@EnableJpaRepositories(basePackages = { "io.atomiclimes.common.dao.repositories" })
@EntityScan(basePackages = "io.atomiclimes.common.dao.entities")
@EnableConfigurationProperties(ProductPlanningProperties.class)
public class ProductPlanningAutoConfiguration {

	@Bean
	public AtomicLimesProductionPlanningCalculation productionPlanningCalculation(
			PlannedProductionRepository plannedProductionRepository,
			NonProductionItemRepository nonProductionItemRepository, EntityManager entityManager) {
		return new AtomicLimesProductionPlanningCalculation(plannedProductionRepository, nonProductionItemRepository,
				entityManager);
	}

}
