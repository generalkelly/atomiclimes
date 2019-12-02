package io.atomiclimes.web.gui.wicket.pages;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.openjson.JSONObject;

import io.atomiclimes.common.dao.entities.PlannedProduction;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.common.dao.repositories.NonProductionItemRepository;
import io.atomiclimes.common.dao.repositories.PlannedProductionRepository;
import io.atomiclimes.common.dao.repositories.ProductRepository;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;
import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.common.logic.AtomicLimesProductionPlanningCalculation;
import io.atomiclimes.helper.jackson.AtomicLimesJacksonHelper;
import io.atomiclimes.web.gui.log.AtomicLimesGuiLogMessages;
import io.atomiclimes.web.gui.wicket.ajax.BootstrapModalAjaxBehaviour;

public class AtomicLimesPlannedProductionPage extends AtomicLimesDefaultWebPage {

	private static final String RESPONSE_TYPE = "application/json";
	private static final String RESPONSE_ENCODING = "UTF-8";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesPlannedProductionPage.class);

	@SpringBean
	private ProductionItemRepository productionItemRepository;
	@SpringBean
	private PlannedProductionRepository plannedProductionRepository;
	@SpringBean
	private ProductRepository productRepository;
	@SpringBean
	private NonProductionItemRepository nonProductionItemRepository;
	@SpringBean
	private AtomicLimesProductionPlanningCalculation productionPlanningCalculation;

	public AtomicLimesPlannedProductionPage() {

		BootstrapModalAjaxBehaviour plannedProductionToCalculate = new BootstrapModalAjaxBehaviour("calculate",
				"preceedingPlannedProduction", "addedPlannedProduction", "subsequentPlannedProduction",
				"plannedProduction") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				RequestCycle requestCycle = RequestCycle.get();

				PlannedProduction preceedingPlannedProduction = getParameterValue("preceedingPlannedProduction",
						new TypeReference<PlannedProduction>() {
						});
				PlannedProduction addedPlannedProduction = getParameterValue("addedPlannedProduction",
						new TypeReference<PlannedProduction>() {
						});
				PlannedProduction subsequentPlannedProduction = getParameterValue("subsequentPlannedProduction",
						new TypeReference<PlannedProduction>() {
						});
				List<PlannedProduction> plannedProduction = getParameterValue("plannedProduction",
						new TypeReference<List<PlannedProduction>>() {
						});

				productionPlanningCalculation.calculateRules(preceedingPlannedProduction, addedPlannedProduction,
						subsequentPlannedProduction);

				plannedProduction = productionPlanningCalculation.addItemToPlannedProduction(
						preceedingPlannedProduction, addedPlannedProduction, subsequentPlannedProduction,
						plannedProduction);

				AtomicLimesJacksonHelper jacksonHelper = new AtomicLimesJacksonHelper(Iterable.class);
				String jsonResponse = new JSONObject().toString();
				if (!plannedProduction.isEmpty()) {
					jsonResponse = jacksonHelper.serialize(plannedProduction);
				}

				requestCycle.scheduleRequestHandlerAfterCurrent(
						new TextRequestHandler(RESPONSE_TYPE, RESPONSE_ENCODING, jsonResponse));
			}

		};

		BootstrapModalAjaxBehaviour saveOrUpdateProductionPlanning = new BootstrapModalAjaxBehaviour(
				"saveOrUpdateProductionPlanning", "plannedProduction") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				List<PlannedProduction> plannedProduction = getParameterValue("plannedProduction",
						new TypeReference<List<PlannedProduction>>() {
						});
				plannedProductionRepository.saveAll(plannedProduction);
			}

		};

		BootstrapModalAjaxBehaviour deletePlannedProductionItem = new BootstrapModalAjaxBehaviour(
				"deletePlannedProductionItem", "plannedProductionItem") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				PlannedProduction plannedProductionItem = getParameterValue("plannedProductionItem",
						new TypeReference<PlannedProduction>() {
						});
				plannedProductionRepository.delete(plannedProductionItem);
			}

		};

		BootstrapModalAjaxBehaviour getProductionPlanningByDate = new BootstrapModalAjaxBehaviour(
				"getProductionPlanningByDate", "date") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				RequestCycle requestCycle = RequestCycle.get();
				String dateString = getParameterValue("date");

				Optional<Set<PlannedProduction>> plannedProduction = plannedProductionRepository
						.findPlannedProductionByDate(LocalDate.parse(dateString));

				String jsonResponse = new JSONObject().toString();
				if (plannedProduction.isPresent()) {
					AtomicLimesJacksonHelper jacksonHelper = new AtomicLimesJacksonHelper(Iterable.class);
					jsonResponse = jacksonHelper.serialize(plannedProduction.get());
				}
				requestCycle.scheduleRequestHandlerAfterCurrent(
						new TextRequestHandler(RESPONSE_TYPE, RESPONSE_ENCODING, jsonResponse));

			}
		};

		BootstrapModalAjaxBehaviour getProductionItems = new BootstrapModalAjaxBehaviour("getProductionItems") {

			private static final long serialVersionUID = 1L;

//			@Override
//			public void renderHead(Component component, IHeaderResponse response) {
//				super.renderHead(component, response);
//				response.render(
//						OnDomReadyHeaderItem.forScript("window.callbackUrl = '" + this.getCallbackUrl() + "';"));
//			}
//
//			@Override
//			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
//				super.updateAjaxAttributes(attributes);
//				attributes.setChannel(new AjaxChannel("json"));
//				attributes.setWicketAjaxResponse(false);
//			}

			@Override
			protected void respond(AjaxRequestTarget target) {
				RequestCycle requestCycle = RequestCycle.get();

				AtomicLimesJacksonHelper jacksonHelper = new AtomicLimesJacksonHelper(Iterable.class);
				Iterable<ProductionItem> productionItems = productionItemRepository.findAll();
				String jsonResponse = jacksonHelper.serialize(productionItems);

				requestCycle.scheduleRequestHandlerAfterCurrent(
						new TextRequestHandler(RESPONSE_TYPE, RESPONSE_ENCODING, jsonResponse));
			}

		};

		this.add(plannedProductionToCalculate);
		this.add(getProductionPlanningByDate);
		this.add(getProductionItems);
		this.add(saveOrUpdateProductionPlanning);
		this.add(deletePlannedProductionItem);

	}

}
