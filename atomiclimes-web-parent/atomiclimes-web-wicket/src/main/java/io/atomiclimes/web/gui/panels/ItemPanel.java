package io.atomiclimes.web.gui.panels;

import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class ItemPanel<T, C> extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Form<T> form;

	public ItemPanel(String id, IModel<T> model) {
		super(id, model);
		IModel<T> compound = new CompoundPropertyModel<T>(model);
		form = new Form<T>("form", compound);
		this.add(form);
	}

	public void addTextField(String id) {
		this.form.add(new TextField<>(id));
	}

	public void addChoicesList(String id, IModel<C> model, List<? extends C> choices) {
		this.form.add();
	}
	
	

}
