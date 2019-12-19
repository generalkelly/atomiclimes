package io.atomiclimes.web.gui.productionplanning;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.repositories.ProductionItemRepository;

@WicketHomePage
@MountPath("home")
public class AtomicLimesMainPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductionItemRepository productionItemRepository;

	public AtomicLimesMainPage() {
//		this.add(new ProductionItemListView("productionItems", getProductionItems()));
	}

//	private List<ProductionItem> getProductionItems() {
//		return productionItemRepository.findAll();
//	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
//		response.render(CssReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "css/main.css")));
//		response.render(CssReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "css/time.css")));
//		response.render(CssReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "css/date.css")));
//		response.render(CssReferenceHeaderItem.forReference(
//				new PackageResourceReference(AtomicLimesMainPage.class, "css/production-item-chooser.css")));
//		response.render(CssReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "css/submit-production.css")));
//		response.render(JavaScriptReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "js/bundle.js")));
//		response.render(JavaScriptReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "js/time.js")));
//		response.render(JavaScriptReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "js/date.js")));
//		response.render(JavaScriptReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "js/production_item.js")));
//		response.render(JavaScriptReferenceHeaderItem
//				.forReference(new PackageResourceReference(AtomicLimesMainPage.class, "js/submit_production.js")));
//		response.render(JavaScriptReferenceHeaderItem
//				.forReference(new WebjarsJavaScriptResourceReference("/js-cookie/js.cookie.js")));

	}

}
