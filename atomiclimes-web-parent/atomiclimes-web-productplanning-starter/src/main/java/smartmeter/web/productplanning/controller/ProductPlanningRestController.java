package smartmeter.web.productplanning.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import smartmeter.common.dao.entities.PlannedProduction;
import smartmeter.common.dao.entities.ProductionItem;
import smartmeter.common.dao.repositories.PlannedProductionRepository;
import smartmeter.common.dao.repositories.ProductionItemRepository;
import smartmeter.web.productplanning.ProductPlanningProperties;

@Controller
public class ProductPlanningRestController {

	private PlannedProductionRepository plannedProductionRepository;
	private ProductionItemRepository productionItemRepository;

	public ProductPlanningRestController(ProductPlanningProperties properties,
			PlannedProductionRepository plannedProductionRepository,
			ProductionItemRepository productionItemRepository) {
		this.plannedProductionRepository = plannedProductionRepository;
		this.productionItemRepository = productionItemRepository;
	}

	@RequestMapping(value = "/plannedProduction", method = RequestMethod.GET)
	public String getListOfItems(Model model) {
		List<PlannedProduction> plannedProductions = plannedProductionRepository.findAll();
		model.addAttribute("plannedProductions", plannedProductions);
		return "plannedProduction.html";
	}

	@RequestMapping(value = "/plannedProduction/addItem", method = RequestMethod.GET)
	public String addItem(Model model) {
		List<ProductionItem> productionItems = productionItemRepository.findAll();
		model.addAttribute("productionItems", productionItems);
		model.addAttribute("plannedProduction", new PlannedProduction());
		model.addAttribute("dateTime", new String());
		return "addItem.html";
	}

	@RequestMapping(value = "/plannedProduction/addItem", method = RequestMethod.POST)
	public String addItem(@RequestBody PlannedProduction plannedProduction)
//	,
//			@ModelAttribute("productionItem") ProductionItem productionItem)
{
		productionItemRepository.saveAndFlush(plannedProduction.getProductionItem());
		plannedProductionRepository.saveAndFlush(plannedProduction);
		return "redirect:/plannedProduction";
	}

	@RequestMapping(value = "/plannedProduction/deleteItem", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute("id") long id) {
		plannedProductionRepository.deleteById(id);
		return "redirect:/plannedProduction";
	}

}
