package io.atomiclimes.web.gui.wicket.pages;

import java.time.OffsetDateTime;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

import antlr.collections.List;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;
import io.atomiclimes.helper.jackson.AtomicLimesJacksonHelper;
import io.atomiclimes.web.gui.wicket.ajax.BootstrapModalAjaxBehaviour;

public class AtomicLimesPlannedProductionPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductionItemRepository productionItemRepository;
	@SpringBean
	private PlannedProductionRepository plannedProductionRepository;

	public AtomicLimesPlannedProductionPage() {

		BootstrapModalAjaxBehaviour plannedProductionToCalculate = new BootstrapModalAjaxBehaviour("calculate") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				RequestCycle requestCycle = RequestCycle.get();

				System.out
						.println(getRequest().getRequestParameters().getParameterValue("preceedingPlannedProduction"));

				AtomicLimesJacksonHelper jacksonHelper = new AtomicLimesJacksonHelper(Iterable.class);
				Iterable<ProductionItem> plannedProductions = productionItemRepository.findAll();
				String jsonResponse = jacksonHelper.serialize(plannedProductions);

				requestCycle.scheduleRequestHandlerAfterCurrent(
						new TextRequestHandler("application/json", "UTF-8", jsonResponse));
			}

		};
		plannedProductionToCalculate.addCallbackParameter("preceedingPlannedProduction");
		plannedProductionToCalculate.addCallbackParameter("addedPlannedProduction");
		plannedProductionToCalculate.addCallbackParameter("subsequentPlannedProduction");

		BootstrapModalAjaxBehaviour getProductionPlanningByDate = new BootstrapModalAjaxBehaviour(
				"getProductionPlanningFor") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				String dateString = getRequest().getRequestParameters().getParameterValue("date").toString();
				plannedProductionRepository.findPlannedProductionByDate(OffsetDateTime.parse(dateString).toLocalDate());
			}
		};

		getProductionPlanningByDate.addCallbackParameter("date");

		BootstrapModalAjaxBehaviour getProductionItems = new BootstrapModalAjaxBehaviour("getProductionItems") {

			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response) {
				super.renderHead(component, response);
				response.render(
						OnDomReadyHeaderItem.forScript("window.callbackUrl = '" + this.getCallbackUrl() + "';"));
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
				attributes.setChannel(new AjaxChannel("json"));
				attributes.setWicketAjaxResponse(false);
			}

			@Override
			protected void respond(AjaxRequestTarget target) {
				RequestCycle requestCycle = RequestCycle.get();

				AtomicLimesJacksonHelper jacksonHelper = new AtomicLimesJacksonHelper(Iterable.class);
				Iterable<ProductionItem> productionItems = productionItemRepository.findAll();
				String jsonResponse = jacksonHelper.serialize(productionItems);

				requestCycle.scheduleRequestHandlerAfterCurrent(
						new TextRequestHandler("application/json", "UTF-8", jsonResponse));
			}

		};

		this.add(plannedProductionToCalculate);
		this.add(getProductionPlanningByDate);
		this.add(getProductionItems);
	}

}
