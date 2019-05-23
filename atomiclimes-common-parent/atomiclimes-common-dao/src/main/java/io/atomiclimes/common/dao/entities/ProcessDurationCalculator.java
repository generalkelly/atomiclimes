package io.atomiclimes.common.dao.entities;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import io.atomiclimes.common.dao.enums.PackagingUnit;

public class ProcessDurationCalculator {

	public void calculate(PlannedProduction plannedProduction) {
		double quantity = plannedProduction.getQuantity();
		PackagingUnit productionUnit = plannedProduction.getUnit();
		List<Packaging> packagingList = plannedProduction.getProductionItem().getPackaging();
		Iterator<Packaging> packagingIterator = packagingList.iterator();
		Packaging packaging = null;
		while (packagingIterator.hasNext()) {
			packaging = packagingIterator.next();
			if (packaging.getPackagingOrder() == 0) {
				break;
			}
		}
		Duration processDuration = calculateDuration(quantity, productionUnit, packaging);
		plannedProduction.setEstimatedProductionDuration(processDuration);
	}

	private Duration calculateDuration(double quantity, PackagingUnit productionUnit, Packaging packaging) {
		double factorOfProductionUnit = productionUnit.getFactor();
		double factorOfPackagingUnit = packaging.getUnit().getFactor();

		return packaging.getDuration().multipliedBy(
				(long) (factorOfProductionUnit / factorOfPackagingUnit * quantity / packaging.getCapacity()));

	}

	public void calculate(PlannedNonproductiveStage plannedNonproductiveStage) {
		Duration duration = plannedNonproductiveStage.getNonProductionItem().getDuration();
		plannedNonproductiveStage.setEstimatedProductionDuration(duration);
	}

}
