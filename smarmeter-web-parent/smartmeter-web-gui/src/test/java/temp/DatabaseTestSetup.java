package temp;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import smartmeter.common.dao.entities.Product;
import smartmeter.common.dao.entities.Constraint;
import smartmeter.common.dao.entities.Packaging;
import smartmeter.common.dao.entities.PlannedProduction;
import smartmeter.common.dao.entities.ProductionItem;
import smartmeter.common.dao.enums.PackagingUnit;
import smartmeter.common.dao.repositories.PlannedProductionRepository;
import smartmeter.common.dao.repositories.ProductRepository;
import smartmeter.common.dao.repositories.ProductionConstraintRepository;
import smartmeter.common.dao.repositories.ProductionItemRepository;
import smartmeter.web.gui.SmartMeterWebGuiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartMeterWebGuiApplication.class)
public class DatabaseTestSetup {

	@Autowired
	PlannedProductionRepository plannedProductionRepository;
	@Autowired
	ProductionItemRepository productionItemRepository;
	@Autowired
	ProductionConstraintRepository productionConstraintRepository;
	@Autowired
	ProductRepository productRepository;

	@Before
	public void setup() {

		Constraint cip = new Constraint();
		cip.setName("CIP");
		cip.setProcessDuration(Duration.ofMinutes(57));
		Constraint constraint = productionConstraintRepository.save(cip);
		List<Constraint> constraints = new LinkedList<>();
		constraints.add(constraint);

		Product eistee = new Product();
		eistee.setName("Eistee");
//		eistee = productRepository.save(eistee);

		Product bier = new Product();
		bier.setName("Bier");
//		bier = productRepository.save(bier);
		ProductionItem eisteeProduction = new ProductionItem();
		eisteeProduction.setProduct(eistee);
		List<Packaging> packaging = new LinkedList<>();
		Packaging flasche05 = new Packaging();
		flasche05.setName("Flasche 0.5L");
		flasche05.setCapacity(0.5);
		flasche05.setPackagingOrder(1);
		flasche05.setUnit(PackagingUnit.LITERS);
		Packaging kasten10 = new Packaging();
		kasten10.setName("Kasten10");
		kasten10.setCapacity(10);
		kasten10.setPackagingOrder(2);
		kasten10.setUnit(PackagingUnit.UNITS);
		packaging.add(flasche05);
		packaging.add(kasten10);
		eisteeProduction.setPackaging(packaging);
		productionItemRepository.saveAndFlush(eisteeProduction);
//
		ProductionItem bierProduction = new ProductionItem();
		bierProduction.setProduct(bier);
		productionItemRepository.saveAndFlush(bierProduction);

		PlannedProduction plannedEisteeProduction = new PlannedProduction();
		plannedEisteeProduction.setEstimatedProductionDuration(Duration.ofHours(3));
		plannedEisteeProduction.setPlannedProductionTime(OffsetDateTime.now());
		plannedEisteeProduction.setProductionItem(eisteeProduction);
		plannedEisteeProduction.setConstraints(constraints);
		plannedProductionRepository.save(plannedEisteeProduction);

		PlannedProduction plannedBierProduction = new PlannedProduction();
		plannedBierProduction.setEstimatedProductionDuration(Duration.ofHours(3));
		plannedBierProduction.setPlannedProductionTime(OffsetDateTime.now());
		plannedBierProduction.setProductionItem(bierProduction);
		plannedBierProduction.setConstraints(constraints);
		plannedProductionRepository.save(plannedBierProduction);

	}

	@Test
	public void testDataBaseEntries() {

	}

}
