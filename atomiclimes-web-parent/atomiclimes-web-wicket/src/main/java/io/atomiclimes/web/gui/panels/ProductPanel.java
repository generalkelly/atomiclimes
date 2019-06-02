package io.atomiclimes.web.gui.panels;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import io.atomiclimes.common.dao.entities.Product;

public class ProductPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductPanel(String id, IModel<Product> productModel) {
		super(id, productModel);
		IModel<Product> compound = new CompoundPropertyModel<Product>(productModel);
		Form<Product> form = new Form<Product>("form", compound);
		this.add(form);
		form.add(new TextField<>("name"));
	}

}
