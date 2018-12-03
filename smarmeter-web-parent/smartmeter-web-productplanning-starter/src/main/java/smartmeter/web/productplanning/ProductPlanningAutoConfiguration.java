package smartmeter.web.productplanning;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import smartmeter.common.dao.repositories.PlannedProductionRepository;
import smartmeter.web.productplanning.controller.ProductPlanningRestController;

@Configuration
@EnableJpaRepositories(basePackages = { "smartmeter.common.dao.repositories" })
@EntityScan(basePackages = "smartmeter.common.dao.entities")
@EnableConfigurationProperties(ProductPlanningProperties.class)
public class ProductPlanningAutoConfiguration {

	private ProductPlanningProperties properties;
	private PlannedProductionRepository plannedProductionRepository;

	public ProductPlanningAutoConfiguration(ProductPlanningProperties properties,
			PlannedProductionRepository plannedProductionRepository) {
		this.properties = properties;
		this.plannedProductionRepository = plannedProductionRepository;
	}

	@Bean
	ProductPlanningRestController controller() {
		return new ProductPlanningRestController(this.properties, this.plannedProductionRepository);
	}

}
