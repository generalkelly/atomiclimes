package smartmeter.web.productplanning.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import smartmeter.common.dao.entities.PlannedProduction;
import smartmeter.common.dao.repositories.PlannedProductionRepository;
import smartmeter.web.productplanning.ProductPlanningProperties;

@Controller
public class ProductPlanningRestController {

	private ProductPlanningProperties properties;
	private PlannedProductionRepository plannedProductionRepository;

	public ProductPlanningRestController(ProductPlanningProperties properties,
			PlannedProductionRepository plannedProductionRepository) {
		this.properties = properties;
		this.plannedProductionRepository = plannedProductionRepository;
	}

	@RequestMapping(value = "/plannedProduction", method = RequestMethod.GET)
	public String getListOfItems(Model model) {
		List<PlannedProduction> plannedProductions = plannedProductionRepository.findAll();
		model.addAttribute("plannedProductions", plannedProductions);
		return "plannedProduction.html";
	}

	@RequestMapping(value = "/plannedProduction/addItem", method = RequestMethod.POST)
	public String addItem(Model model) {
		return "plannedProduction.html";
	}

	@RequestMapping(value = "/plannedProduction/deleteItem", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute("id") long id) {
		plannedProductionRepository.deleteById(id);
		return "redirect:/plannedProduction";
	}

}
