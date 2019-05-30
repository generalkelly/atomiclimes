package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.head.IHeaderResponse;

import io.atomiclimes.web.gui.wicket.ajax.BootstrapModalAjaxBehaviour;

public class AtomicLimesPlannedProductionPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtomicLimesPlannedProductionPage() {
		BootstrapModalAjaxBehaviour plannedProductionToCalculate = new BootstrapModalAjaxBehaviour(
				"plannedProductionToCalculate");
		plannedProductionToCalculate.addCallbackParameter("plannedProductionToCalculate");
		this.add(plannedProductionToCalculate);
		BootstrapModalAjaxBehaviour getProductionPlanningByDate = new BootstrapModalAjaxBehaviour(
				"getProductionPlanningByDate");
		getProductionPlanningByDate.addCallbackParameter("getProductionPlanningByDate");
		this.add(getProductionPlanningByDate);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
	}

}
