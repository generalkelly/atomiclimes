package smartmeter.web.gui;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import smartmeter.common.dao.entities.ProductionItem;
import smartmeter.common.dao.repositories.ProductionItemRepository;
import smartmeter.web.gui.panels.HeaderPanel;

public class SmartmeterMainPage extends WebPage {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ProductionItemRepository productionItemRepository;

	private Component headerPanel;

	public SmartmeterMainPage() {
		this.add(headerPanel = new HeaderPanel("headerPanel"));
		this.add(new ProductionItemListView("productionItems", getProductionItems()));
		this.add(new AddProductionItemAjaxBehaviour());
		this.add(new HeaderResponseContainer("footer-container", "footer-container"));
	}

	private List<ProductionItem> getProductionItems() {
		return productionItemRepository.findAll();
	}

	public Component getHeaderPanel() {
		return headerPanel;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "css/main.css")));
		response.render(CssReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "css/time.css")));
		response.render(CssReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "css/date.css")));
		response.render(CssReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "css/production-item-chooser.css")));
		response.render(JavaScriptReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "js/bundle.js")));
		response.render(JavaScriptReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "js/time.js")));
		response.render(JavaScriptReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "js/date.js")));
		response.render(JavaScriptReferenceHeaderItem
				.forReference(new PackageResourceReference(SmartmeterMainPage.class, "js/production_item.js")));

	}

}
