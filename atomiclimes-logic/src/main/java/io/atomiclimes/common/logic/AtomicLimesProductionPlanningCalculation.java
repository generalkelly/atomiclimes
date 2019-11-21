package io.atomiclimes.common.logic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import io.atomiclimes.common.dao.entities.NonProductionItem;
import io.atomiclimes.common.dao.entities.PlannedNonproductiveStage;
import io.atomiclimes.common.dao.entities.PlannedProduction;
import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.common.dao.entities.ProductionStage;
import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;

public class AtomicLimesProductionPlanningCalculation {

	private KieServices kieServices;
	private Resource resource;
	private KieFileSystem kieFileSystem;
	private KieBuilder kieBuilder;
	private KieRepository kieRepository;
	private ReleaseId krDefaultReleaseId;
	private KieContainer kieContainer;
	private PlannedProductionRepository plannedProductionRepository;
	private NonProductionItemRepository nonProductionItemRepository;

	public AtomicLimesProductionPlanningCalculation() {

	}

	public AtomicLimesProductionPlanningCalculation(PlannedProductionRepository plannedProductionRepository,
			NonProductionItemRepository nonProductionItemRepository) {
		this();
		this.plannedProductionRepository = plannedProductionRepository;
		this.nonProductionItemRepository = nonProductionItemRepository;

		this.kieServices = KieServices.Factory.get();
		this.resource = ResourceFactory.newClassPathResource("Productmatrix.xls", getClass());
		this.kieFileSystem = kieServices.newKieFileSystem().write(resource);
		this.kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		this.kieBuilder.buildAll();
		this.kieRepository = kieServices.getRepository();
		this.krDefaultReleaseId = kieRepository.getDefaultReleaseId();
		this.kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
	}

	public List<ProductionStage> calculate(PlannedProduction preceedingPlannedProduction,
			PlannedProduction addedPlannedProduction, PlannedProduction subsequentPlannedProduction) {

		Optional<PlannedProduction> preceeding = Optional.ofNullable(preceedingPlannedProduction);
		Optional<PlannedProduction> subsequent = Optional.ofNullable(subsequentPlannedProduction);

		Set<PlannedNonproductiveStage> preceedingPlannedNonProductiveStages = new HashSet<>();
		if (preceeding.isPresent()) {
			preceedingPlannedNonProductiveStages = preceeding.get().getSubsequentPlannedNonproductiveStages();
		} else {
			preceeding = this.plannedProductionRepository
					.findPreceedingPlannedProductionOf(addedPlannedProduction.getPlannedProductionTime());
			if (preceeding.isPresent()) {
				preceedingPlannedProduction = preceeding.get();
				preceedingPlannedNonProductiveStages = preceeding.get().getSubsequentPlannedNonproductiveStages();
			} else {
				preceedingPlannedProduction = newEmptyPlannedProduction();
			}
		}

		if (!subsequent.isPresent()) {
			subsequent = this.plannedProductionRepository
					.findSubsequentPlannedProductionOf(addedPlannedProduction.getPlannedProductionTime());
			if (subsequent.isPresent()) {
				subsequentPlannedProduction = subsequent.get();
			} else {
				subsequentPlannedProduction = newEmptyPlannedProduction();
			}
		}

		final List<String> preceedingPlannedNonProductiveStageTypes = new LinkedList<>();
		final List<String> subsequentPlannedNonProductiveStageTypes = new LinkedList<>();

//		Rule set is fired on the preceeding PlannedProduction and the new one
		ProductionItem preceedingProductionItem = preceedingPlannedProduction.getProductionItem();
		ProductionItem newProductionItem = addedPlannedProduction.getProductionItem();
		ProductionItem subsequentProductionItem = subsequentPlannedProduction.getProductionItem();

		runRulesEngine(preceedingPlannedNonProductiveStageTypes, preceedingProductionItem, newProductionItem);

		runRulesEngine(subsequentPlannedNonProductiveStageTypes, newProductionItem, subsequentProductionItem);

		final Set<PlannedNonproductiveStage> preceedingPlannedNonProductiveStagesFromRulesEngine = extractNonProductiveStages(
				preceedingPlannedNonProductiveStageTypes);

		final Set<PlannedNonproductiveStage> subsequentPlannedNonProductiveStagesFromRulesEngine = extractNonProductiveStages(
				subsequentPlannedNonProductiveStageTypes);

		removeOverlappingNonProductiveStages(preceedingPlannedNonProductiveStages,
				preceedingPlannedNonProductiveStagesFromRulesEngine);

		final List<ProductionStage> productionStages = new LinkedList<>();
		productionStages.addAll(preceedingPlannedNonProductiveStagesFromRulesEngine);
		productionStages.add(addedPlannedProduction);
		productionStages.addAll(subsequentPlannedNonProductiveStagesFromRulesEngine);

		productionStages.stream().forEach(p -> System.out.println(p.getEstimatedProductionDuration()));

		return productionStages;
	}

	private PlannedProduction newEmptyPlannedProduction() {
		PlannedProduction plannedProduction = new PlannedProduction();
		ProductionItem productionItem = new ProductionItem();
		Product product = new Product();
		productionItem.setProduct(product);
		plannedProduction.setProductionItem(productionItem);
		return plannedProduction;
	}

	private void runRulesEngine(final List<String> linkingNonProductiveStageTypes,
			ProductionItem preceedingProductionItem, ProductionItem newProductionItem) {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(preceedingProductionItem);
		kieSession.setGlobal("nonProductiveStages", linkingNonProductiveStageTypes);
		kieSession.setGlobal("newProductionItem", newProductionItem);
		kieSession.fireAllRules();
		kieSession.dispose();
	}

	private Set<PlannedNonproductiveStage> extractNonProductiveStages(final List<String> nonProductiveStageTypes) {
		final Set<PlannedNonproductiveStage> plannedNonproductiveStages = new HashSet<>();
		for (String preceedingPlannedNonProductiveStageType : nonProductiveStageTypes) {
			NonProductionItem nonProductionItem = this.nonProductionItemRepository
					.findByName(preceedingPlannedNonProductiveStageType);
			PlannedNonproductiveStage nonproductiveStage = new PlannedNonproductiveStage();
			nonproductiveStage.setNonProductionItem(nonProductionItem);
			plannedNonproductiveStages.add(nonproductiveStage);
		}
		return plannedNonproductiveStages;
	}

	private void removeOverlappingNonProductiveStages(
			Set<PlannedNonproductiveStage> preceedingPlannedNonProductiveStages,
			Set<PlannedNonproductiveStage> preceedingPlannedNonProductiveStagesFromRulesEngine) {
		if (!preceedingPlannedNonProductiveStages.isEmpty()) {
			for (PlannedNonproductiveStage nonproductiveStage : preceedingPlannedNonProductiveStages) {
				for (PlannedNonproductiveStage nonproductiveStageFromRulesEngine : preceedingPlannedNonProductiveStagesFromRulesEngine) {
					if (nonproductiveStage.getNonProductionItem().getName() == nonproductiveStageFromRulesEngine
							.getNonProductionItem().getName()) {
						preceedingPlannedNonProductiveStagesFromRulesEngine.remove(nonproductiveStageFromRulesEngine);
						break;
					}
				}
			}
		}
	}

}
