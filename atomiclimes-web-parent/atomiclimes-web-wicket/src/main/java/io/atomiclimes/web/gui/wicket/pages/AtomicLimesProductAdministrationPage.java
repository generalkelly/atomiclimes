package io.atomiclimes.web.gui.wicket.pages;

import java.util.List;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.repositories.ProductRepository;
import io.atomiclimes.web.gui.panels.EditItemPanel;

public class AtomicLimesProductAdministrationPage extends AtomicLimesDefaultWebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductRepository productRepository;

	public AtomicLimesProductAdministrationPage() {
		this.add(new ProductListView(getProducts()));
		this.add(new BookmarkablePageLink<>("addProductPage", AtomicLimesProductPage.class));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
	}

	private class ProductListView extends ListView<Product> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ProductListView(List<Product> products) {
			super("products", products);
		}

		@Override
		protected void populateItem(ListItem<Product> item) {
			Product product = item.getModelObject();
			item.add(new Label("name", product.getName()));
			item.add(new EditItemPanel<Product>("editItem", Model.of(product), productRepository) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void edit() {
					PageParameters parameters = new PageParameters();
					parameters.add("name", product.getName());
					setResponsePage(AtomicLimesProductPage.class, parameters);
				}

				@Override
				protected void delete() {
					productRepository.delete(product);
					setResponsePage(AtomicLimesProductAdministrationPage.class);
				}

			});
		}

	}

	private List<Product> getProducts() {
		return this.productRepository.findAll();
	}

}
