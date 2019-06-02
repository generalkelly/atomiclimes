package io.atomiclimes.web.gui.wicket.pages;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ItemPanel<T> extends Panel {

	private static final long serialVersionUID = 1L;

	public ItemPanel(String id, IModel<?> model) {
		super(id, model);
	}

}