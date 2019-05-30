package io.atomiclimes.web.gui.wicket.pages;

import java.util.List;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import io.atomiclimes.common.dao.entities.Product;
import io.atomiclimes.common.dao.repositories.ProductRepository;

public class AtomicLimesProductAdministrationPage extends AtomicLimesDefaultWebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProductRepository productRepository;

	public AtomicLimesProductAdministrationPage() {
		this.add(new ProductListView(getProducts()));
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
//			item.add(new TextField<Product>("name", new PropertyModel<Product>(product, "name")));

		}

	}

	private List<Product> getProducts() {
		return this.productRepository.findAll();
	}

}
