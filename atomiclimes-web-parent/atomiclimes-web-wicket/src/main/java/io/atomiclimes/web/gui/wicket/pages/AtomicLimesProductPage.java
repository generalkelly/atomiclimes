package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.repositories.ProductRepository;
import io.atomiclimes.web.gui.panels.ProductPanel;

@WicketHomePage
@MountPath("admin/products/product")
public class AtomicLimesProductPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductRepository productRepository;

	public AtomicLimesProductPage() {
		this(Model.of(new Product()));
	}

	public AtomicLimesProductPage(IModel<Product> productModel) {

		Form<Product> form = new Form<Product>("form");
		form.add(new ProductPanel("product", productModel));
		this.add(form);
		form.add(new Button("save") {

			public void onSubmit() {
				productRepository.save(productModel.getObject());
				setResponsePage(AtomicLimesProductAdministrationPage.class);
			}
		});

	}

}
