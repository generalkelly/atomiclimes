package io.atomiclimes.common.logic;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import io.atomiclimes.common.dao.entities.NonProductionItem;
import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.entities.PlannedNonproductiveStage;
import io.atomiclimes.common.dao.entities.PlannedProduction;
import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.common.dao.entities.ProductionStage;
import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.common.dao.repositories.PackagingRepository;
import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;
import io.atomiclimes.common.dao.repositories.ProductRepository;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;
import io.atomiclimes.common.helper.enums.PackagingUnit;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = "io.atomiclimes.common.dao.repositories")
@EntityScan(basePackages = "io.atomiclimes.common.dao.entities")
public class AtomicLimesProductionPlanningCalculationTest {

	private AtomicLimesProductionPlanningCalculation calculation;

	@Autowired
	private ProductionItemRepository productionItemRepository;
	@Autowired
	private PlannedProductionRepository plannedProductionRepository;
	@Autowired
	private NonProductionItemRepository nonProductionItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PackagingRepository packagingRepository;

	private PlannedProduction preceedingPlannedProduction;
	private PlannedProduction newPlannedProduction;
	private PlannedProduction subsequentPlannedProduction;
	private PlannedProduction lastPlannedProductionOfPreviousDays;

	private PlannedProduction nextPlannedProductionOnUpcommingDays;

	@Before
	public void setup() {
		calculation = new AtomicLimesProductionPlanningCalculation(plannedProductionRepository,
				nonProductionItemRepository);
		NonProductionItem foo = new NonProductionItem();
		foo.setName("FOO");
		foo.setDuration(Duration.ofMinutes(30));
		nonProductionItemRepository.save(foo);
		NonProductionItem bar = new NonProductionItem();
		bar.setName("BAR");
		bar.setDuration(Duration.ofMinutes(30));
		nonProductionItemRepository.save(bar);

		ProductionItem fooItem = createProductionItem("foo");

		ProductionItem barItem = createProductionItem("bar");

		ProductionItem bazItem = createProductionItem("baz");

		lastPlannedProductionOfPreviousDays = new PlannedProduction();
		lastPlannedProductionOfPreviousDays.setPlannedProductionTime(OffsetDateTime.now().minusDays(1));
		lastPlannedProductionOfPreviousDays.setProductionItem(barItem);
		lastPlannedProductionOfPreviousDays.setQuantity(10);
		lastPlannedProductionOfPreviousDays.setUnit(PackagingUnit.HECTO_LITERS);
		Set<PlannedNonproductiveStage> subsequentPlannedNonproductiveStages = new HashSet<>();
		PlannedNonproductiveStage nonProductiveStageFoo = new PlannedNonproductiveStage();
		nonProductiveStageFoo.setPlannedProductionTime(OffsetDateTime.now().minusDays(1).plusMinutes(10));
		nonProductiveStageFoo.setNonProductionItem(foo);
		PlannedNonproductiveStage nonProductiveStageBar = new PlannedNonproductiveStage();
		nonProductiveStageBar.setPlannedProductionTime(OffsetDateTime.now().minusDays(1).plusMinutes(20));
		nonProductiveStageBar.setNonProductionItem(bar);
		subsequentPlannedNonproductiveStages.add(nonProductiveStageFoo);
		subsequentPlannedNonproductiveStages.add(nonProductiveStageBar);
		lastPlannedProductionOfPreviousDays
				.setSubsequentPlannedNonproductiveStages(subsequentPlannedNonproductiveStages);
		plannedProductionRepository.save(lastPlannedProductionOfPreviousDays);

		nextPlannedProductionOnUpcommingDays = new PlannedProduction();
		nextPlannedProductionOnUpcommingDays.setPlannedProductionTime(OffsetDateTime.now().plusDays(1));
		nextPlannedProductionOnUpcommingDays.setProductionItem(fooItem);
		nextPlannedProductionOnUpcommingDays.setQuantity(10);
		nextPlannedProductionOnUpcommingDays.setUnit(PackagingUnit.HECTO_LITERS);
		nextPlannedProductionOnUpcommingDays
				.setSubsequentPlannedNonproductiveStages(subsequentPlannedNonproductiveStages);
		plannedProductionRepository.save(nextPlannedProductionOnUpcommingDays);

		preceedingPlannedProduction = new PlannedProduction();
		preceedingPlannedProduction.setPlannedProductionTime(OffsetDateTime.now().plusMinutes(10));
		preceedingPlannedProduction.setProductionItem(fooItem);
		preceedingPlannedProduction.setQuantity(10);
		preceedingPlannedProduction.setUnit(PackagingUnit.HECTO_LITERS);
		plannedProductionRepository.save(preceedingPlannedProduction);

		newPlannedProduction = new PlannedProduction();
		newPlannedProduction.setPlannedProductionTime(OffsetDateTime.now().plusMinutes(20));
		newPlannedProduction.setProductionItem(barItem);
		newPlannedProduction.setQuantity(10);
		newPlannedProduction.setUnit(PackagingUnit.HECTO_LITERS);
		plannedProductionRepository.save(newPlannedProduction);

		subsequentPlannedProduction = new PlannedProduction();
		subsequentPlannedProduction.setPlannedProductionTime(OffsetDateTime.now().plusMinutes(30));
		subsequentPlannedProduction.setProductionItem(bazItem);
		subsequentPlannedProduction.setQuantity(10);
		subsequentPlannedProduction.setUnit(PackagingUnit.HECTO_LITERS);
		plannedProductionRepository.save(subsequentPlannedProduction);
	}

	private ProductionItem createProductionItem(String name) {
		Product product = new Product();
		product.setName(name);
		Packaging packaging = new Packaging();
		packaging.setName("Flasche");
		packaging.setUnit(PackagingUnit.LITERS);
		packaging.setCapacity(0.3);
		packaging.setDuration(Duration.ofSeconds(2));
		packaging.setPackagingOrder(0);
		Set<Packaging> packagingSet = new HashSet<>();
		packagingSet.add(packaging);
		ProductionItem productionItem = new ProductionItem();
		productionItem.setProduct(product);
		productionItem.setPackaging(packagingSet);
		packagingRepository.save(packaging);
		productRepository.save(product);
		return productionItemRepository.save(productionItem);
	}

	@Test
	public void testNewPlannedProductionWithPredecessorAndSuccessor() {
		List<ProductionStage> productionStages = calculation.calculateRules(preceedingPlannedProduction,
				newPlannedProduction, subsequentPlannedProduction);
		Assert.assertEquals(false, productionStages.isEmpty());
	}

	@Test
	public void testPredecessorOnPreviousDay() {
		List<ProductionStage> productionStages = calculation.calculateRules(null, preceedingPlannedProduction,
				newPlannedProduction);
		Assert.assertEquals(false, productionStages.isEmpty());
	}

	@Test
	public void testWithoutPredecessor() {
		List<ProductionStage> productionStages = calculation.calculateRules(null, lastPlannedProductionOfPreviousDays,
				newPlannedProduction);
		Assert.assertEquals(false, productionStages.isEmpty());
	}

	@Test
	public void testSuccessorOnUpcomingDay() {
		List<ProductionStage> productionStages = calculation.calculateRules(newPlannedProduction,
				subsequentPlannedProduction, null);
		Assert.assertEquals(false, productionStages.isEmpty());
	}

	@Test
	public void testWithoutSuccessor() {
		List<ProductionStage> productionStages = calculation.calculateRules(newPlannedProduction,
				nextPlannedProductionOnUpcommingDays, null);
		Assert.assertEquals(false, productionStages.isEmpty());
	}

}
