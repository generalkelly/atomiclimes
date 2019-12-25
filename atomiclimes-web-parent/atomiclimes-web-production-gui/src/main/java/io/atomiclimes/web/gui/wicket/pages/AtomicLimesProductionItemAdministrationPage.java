package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Packaging;
import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.entities.ProductionItem;
import io.atomiclimes.common.dao.repositories.ProductionItemRepository;

@MountPath("admin/productionItem")
public class AtomicLimesProductionItemAdministrationPage extends AtomicLimesItemAdministrationPage<ProductionItem> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductionItemRepository productionItemRepository;

	public AtomicLimesProductionItemAdministrationPage() {
		super();
		super.setItemPageClass(AtomicLimesProductionItemPage.class);
		super.setItemAdministrationPageClass(AtomicLimesProductionItemAdministrationPage.class);
	}

	@Override
	protected Iterable<ProductionItem> getAllItemsFromRepository() {
		return productionItemRepository.findAll();
	}

	@Override
	protected IModel<ProductionItem> generateModel(ProductionItem item) {
		return Model.of(item);
	}

	@Override
	protected void deleteItem(ProductionItem item) {
		productionItemRepository.delete(item);
	}

	@Override
	protected String getNameFromItem(ProductionItem item) {
//		String productName = null;
//		String zeroOrderpackagingName = null;
//		String firstOrderpackagingName = null;
//		if (item != null) {
//			Product product = item.getProduct();
//			if (product != null) {
//				productName = product.getName();
//			}
//			Packaging packaging = item.getPackagingOfOrder(0);
//			if (packaging != null) {
//				zeroOrderpackagingName = packaging.getName();
//			}
//			packaging = item.getPackagingOfOrder(1);
//			if (packaging != null) {
//				firstOrderpackagingName = packaging.getName();
//			}
//		}
//		return productName + ", " + zeroOrderpackagingName + ", " + firstOrderpackagingName;
		return item.toString();
	}

	@Override
	protected BookmarkablePageLink<? extends WebPage> generateBookmarkablePageLink() {
		return new BookmarkablePageLink<>("addItem", AtomicLimesProductionItemPage.class);
	}

	@Override
	protected PageParameters generateResponsePageParameters(ProductionItem item) {
		PageParameters pageParameters = new PageParameters();
		pageParameters.add("id", item.getId());
		pageParameters.add("productName", item.getProduct().getName());
		for (Packaging packaging : item.getPackaging()) {
			pageParameters.add("packaging", packaging.getName());
		}
		return pageParameters;
	}

}
