package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.repositories.ProductRepository;

@MountPath("admin/products/product")
public class AtomicLimesProductPage extends AtomicLimesItemPage<Product> {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductRepository productRepository;

	public AtomicLimesProductPage() {
		super(Model.of(new Product()));
	}

	public AtomicLimesProductPage(PageParameters pageParameters) {
		super(pageParameters);
	}

	public AtomicLimesProductPage(IModel<Product> productModel) {
		super(productModel);
	}

	@Override
	protected Product getItemFromRepository(IModel<Product> model) {
		return this.productRepository.findByName(model.getObject().getName());
	}

	@Override
	protected void saveItem(Product item) {
		this.productRepository.save(item);
	}

	@Override
	protected IModel<Product> mapPageParametersToModel(PageParameters pageParameters) {
		Product product = new Product();
		product.setName(pageParameters.get("name").toString());
		return Model.of(product);
	}

	@Override
	protected void mapModelToItemForUpdate(IModel<Product> model, Product item) {
		item.setName(model.getObject().getName());
	}

	@Override
	protected WebPage generateResponsePage(IModel<Product> model) {
		return new AtomicLimesProductPage(model);
	}

	@Override
	protected WebPage generateAdministrationResponsePage() {
		return new AtomicLimesProductAdministrationPage();
	}

}
