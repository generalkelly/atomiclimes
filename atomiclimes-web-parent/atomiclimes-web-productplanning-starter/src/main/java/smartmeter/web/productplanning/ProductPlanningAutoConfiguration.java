package smartmeter.web.productplanning;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;
import io.atomiclimes.common.logic.AtomicLimesProductionPlanningCalculation;

@Configuration
@EnableJpaRepositories(basePackages = { "io.atomiclimes.common.dao.repositories" })
@EntityScan(basePackages = "io.atomiclimes.common.dao.entities")
@EnableConfigurationProperties(ProductPlanningProperties.class)
public class ProductPlanningAutoConfiguration {

	private ProductPlanningProperties properties;
	private PlannedProductionRepository plannedProductionRepository;
	private ProductionItemRepository productionItemRepository;
	private NonProductionItemRepository nonProductionItemRepository;

	public ProductPlanningAutoConfiguration(ProductPlanningProperties properties,
			PlannedProductionRepository plannedProductionRepository, ProductionItemRepository productionItemRepository,
			NonProductionItemRepository nonProductionItemRepository) {
		this.properties = properties;
		this.plannedProductionRepository = plannedProductionRepository;
		this.productionItemRepository = productionItemRepository;
		this.nonProductionItemRepository = nonProductionItemRepository;

	}

	@Bean
	public AtomicLimesProductionPlanningCalculation productionPlanningCalculation(PlannedProductionRepository plannedProductionRepository, NonProductionItemRepository nonProductionItemRepository) {
		return new AtomicLimesProductionPlanningCalculation(plannedProductionRepository, nonProductionItemRepository);
	}

}
