package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.repositories.ProductRepository;

@WicketHomePage
@MountPath("admin/products")
public class AtomicLimesProductAdministrationPage extends AtomicLimesItemAdministrationPage<Product> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductRepository productRepository;

	public AtomicLimesProductAdministrationPage() {
		super();
		super.setItemPageClass(AtomicLimesProductPage.class);
		super.setItemAdministrationPageClass(AtomicLimesProductAdministrationPage.class);
	}

	@Override
	protected Iterable<Product> getAllItemsFromRepository() {
		return this.productRepository.findAll();
	}

	@Override
	protected IModel<Product> generateModel(Product item) {
		return Model.of(item);
	}

	@Override
	protected void deleteItem(Product item) {
		this.productRepository.delete(item);
	}

	@Override
	protected String getNameFromItem(Product item) {
		return item.getName();
	}

	@Override
	protected BookmarkablePageLink<? extends WebPage> generateBookmarkablePageLink() {
		return new BookmarkablePageLink<>("addProductPage", AtomicLimesProductPage.class);
	}

	@Override
	protected PageParameters generateResponsePageParameters(Product item) {
		PageParameters pageParameters = new PageParameters();
		pageParameters.add("name", item.getName());
		return pageParameters;
	}

}
