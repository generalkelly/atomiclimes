package smartmeter.web.productplanning;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "io.atomiclimes.common.dao.repositories" })
@EntityScan(basePackages = "io.atomiclimes.common.dao.entities")
@EnableConfigurationProperties(ProductPlanningProperties.class)
public class ProductPlanningAutoConfiguration {

	private ProductPlanningProperties properties;
	private PlannedProductionRepository plannedProductionRepository;
	private ProductionItemRepository productionItemRepository;

	public ProductPlanningAutoConfiguration(ProductPlanningProperties properties,
			PlannedProductionRepository plannedProductionRepository, ProductionItemRepository productionItemRepository) {
		this.properties = properties;
		this.plannedProductionRepository = plannedProductionRepository;
		this.productionItemRepository = productionItemRepository;
		
	}


}
