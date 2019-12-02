package io.atomiclimes.web.gui.panels;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class ItemPanel<T, C> extends Panel {

	private static final long serialVersionUID = 1L;
	private Form<T> form;
	private List<TextField<?>> textFields = new LinkedList<>();

	public ItemPanel(String id, IModel<T> model) {
		super(id, model);
		IModel<T> compound = new CompoundPropertyModel<>(model);
		form = new Form<>("form", compound);
		form.add(new TextFieldListView("textFields", this.textFields));
		this.add(form);
	}

	public void addTextField(Object modelObject, String fieldName) {
		this.textFields.add(new TextField<>("field", new PropertyModel<>(modelObject, fieldName)));
	}

	public void addMultiselect(String id, IModel<? extends Collection<C>> model, List<? extends C> choices) {
		this.form.add(new CheckBoxMultipleChoice<>(id, model, choices));
	}

	private class TextFieldListView extends ListView<TextField<?>> {

		private static final long serialVersionUID = 1L;

		public TextFieldListView(String id, List<TextField<?>> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<TextField<?>> item) {
			item.add(item.getModelObject());
		}

	}

}
