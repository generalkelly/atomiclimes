package io.atomiclimes.common.logic;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

	public List<ProductionStage> calculateRules(PlannedProduction preceedingPlannedProduction,
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

		preceedingPlannedProduction
				.setSubsequentPlannedNonproductiveStages(preceedingPlannedNonProductiveStagesFromRulesEngine);
		addedPlannedProduction
				.setSubsequentPlannedNonproductiveStages(subsequentPlannedNonProductiveStagesFromRulesEngine);

		final List<ProductionStage> productionStages = new LinkedList<>();
		productionStages.addAll(preceedingPlannedNonProductiveStagesFromRulesEngine);
		productionStages.add(addedPlannedProduction);
		productionStages.addAll(subsequentPlannedNonProductiveStagesFromRulesEngine);
		return productionStages;
	}

	public List<PlannedProduction> addItemToPlannedProduction(PlannedProduction preceedingPlannedProduction,
			PlannedProduction addedPlannedProduction, PlannedProduction subsequentPlannedProduction,
			List<PlannedProduction> plannedProduction) {

		// first add new planned production item addedPlannedProduction
		if (plannedProduction == null) {
			plannedProduction = new LinkedList<>();
		}
		plannedProduction.add(addedPlannedProduction);

		reorderPlannedProduction(preceedingPlannedProduction, addedPlannedProduction, subsequentPlannedProduction,
				plannedProduction);

		OffsetDateTime lastUnchangedPlannedProduction = null;

		if ((preceedingPlannedProduction != null)
				&& (preceedingPlannedProduction.getPreceedingPlannedProductionId() != null)) {
			Optional<PlannedProduction> preceedingPreceedingPlannedProduction = plannedProductionRepository
					.findById(preceedingPlannedProduction.getPreceedingPlannedProductionId());

			if (preceedingPreceedingPlannedProduction.isPresent()) {
				lastUnchangedPlannedProduction = preceedingPreceedingPlannedProduction.get().getPlannedProductionTime();
			}
		}

		List<PlannedProduction> sortedPlannedProductionList = new LinkedList<>();
		List<PlannedProduction> plannedProductionWithProductionTimeChange = sortAndFilterAlteredPlannedProductions(
				plannedProduction, lastUnchangedPlannedProduction, sortedPlannedProductionList);

		plannedProductionWithProductionTimeChange = recalculateProductionPlanningTimes(
				plannedProductionWithProductionTimeChange);
		sortedPlannedProductionList.addAll(plannedProductionWithProductionTimeChange);

		return sortedPlannedProductionList;

	}

	private List<PlannedProduction> sortAndFilterAlteredPlannedProductions(
			List<PlannedProduction> plannedProductionByDateSet, OffsetDateTime lastUnchangedPlannedProduction,
			List<PlannedProduction> sortedPlannedProductionList) {

		Comparator<? super PlannedProduction> comparator = (plannedProduction, plannedProductionToCompareWith) -> {
			if (plannedProduction.getPlannedProductionTime()
					.isAfter(plannedProductionToCompareWith.getPlannedProductionTime())) {
				return 1;
			} else if (plannedProduction.getPlannedProductionTime()
					.isBefore(plannedProductionToCompareWith.getPlannedProductionTime())) {
				return -1;
			} else {
				return 0;
			}
		};
		return plannedProductionByDateSet.stream().sorted(comparator).filter(p -> {
			boolean isAfter = true;
			if (lastUnchangedPlannedProduction != null) {
				isAfter = p.getPlannedProductionTime().isAfter(lastUnchangedPlannedProduction);
			}
			if (!isAfter) {
				sortedPlannedProductionList.add(p);
			}
			return isAfter;
		}).collect(Collectors.toList());
	}

	private void reorderPlannedProduction(PlannedProduction preceedingPlannedProduction,
			PlannedProduction addedPlannedProduction, PlannedProduction subsequentPlannedProduction,
			List<PlannedProduction> plannedProduction) {
		addedPlannedProduction.setId(UUID.randomUUID());
		if (preceedingPlannedProduction != null) {
			preceedingPlannedProduction.setSubsequentPlannedProductionId(addedPlannedProduction.getId());
			addedPlannedProduction.setPreceedingPlannedProductionId(preceedingPlannedProduction.getId());
		}
		if (subsequentPlannedProduction != null) {
			subsequentPlannedProduction.setPreceedingPlannedProductionId(addedPlannedProduction.getId());
			addedPlannedProduction.setSubsequentPlannedProductionId(subsequentPlannedProduction.getId());
		}
		// replace preceedingPlannedProduction und subsequentPlannedProduction in the
		// set by the method parameters of the identical name
		plannedProduction.stream().forEach(p -> {
//			in case p is addedPlannedProduction the id is null, so this case has to be sorted out
			if (p.getId() != null) {
				if ((preceedingPlannedProduction != null) && (p.getId().equals(preceedingPlannedProduction.getId()))) {
					p = preceedingPlannedProduction;
				} else if ((subsequentPlannedProduction != null)
						&& (p.getId().equals(subsequentPlannedProduction.getId()))) {
					p = subsequentPlannedProduction;
				}
			}
		});
	}

	private List<PlannedProduction> recalculateProductionPlanningTimes(List<PlannedProduction> plannedProduction) {
		Map<UUID, PlannedProduction> idToPlannedProductionMap = plannedProduction.stream()
				.collect(Collectors.toMap(PlannedProduction::getId, p -> p));

		plannedProduction.stream().map(PlannedProduction::getId).forEach(id -> {
			PlannedProduction p = idToPlannedProductionMap.get(id);
			OffsetDateTime plannedProductionTime = p.getPlannedProductionTime();
			Duration processDuration = p.getEstimatedProductionDuration();
			OffsetDateTime endOfProcess = plannedProductionTime.plus(processDuration);
			for (PlannedNonproductiveStage nonProductiveStage : p.getSubsequentPlannedNonproductiveStages()) {
				nonProductiveStage.setPlannedProductionTime(endOfProcess);
				endOfProcess = endOfProcess.plus(nonProductiveStage.getEstimatedProductionDuration());
			}
			if (p.getSubsequentPlannedProductionId() != null) {
				idToPlannedProductionMap.get(p.getSubsequentPlannedProductionId())
						.setPlannedProductionTime(endOfProcess);
			}
		});

		return idToPlannedProductionMap.entrySet().stream().map(Entry::getValue).collect(Collectors.toList());

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
					if (nonproductiveStage.getNonProductionItem().getName()
							.equals(nonproductiveStageFromRulesEngine.getNonProductionItem().getName())) {
						preceedingPlannedNonProductiveStagesFromRulesEngine.remove(nonproductiveStageFromRulesEngine);
						break;
					}
				}
			}
		}
	}

}
