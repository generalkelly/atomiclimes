package io.atomiclimes.common.helper.wicket;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class ItemPanel<T> extends Panel {

	private static final long serialVersionUID = 1L;
	private Form<T> form;
	private List<Panel> formFields = new LinkedList<>();

	public ItemPanel(String id, IModel<T> model) {
		super(id, model);
		IModel<T> compound = new CompoundPropertyModel<>(model);
		form = new Form<>("form", compound);
		form.add(new FormFieldListView("fields", this.formFields));
		this.add(form);
	}

	public void addTextField(Object modelObject, String fieldName, String formFieldName,
			AtomicLimesConverter<?> converter) {
		TextFieldPanel<T, Object> textFieldPanel = new TextFieldPanel<>(new PropertyModel<T>(modelObject, fieldName));
		textFieldPanel.addField(modelObject, fieldName, formFieldName, converter);
		this.formFields.add(textFieldPanel);
	}

	public void addDropdownChoice(Object modelObject, String fieldName, String formFieldName, List<Object> choices,
			AtomicLimesConverter<?> converter) {
		DropDownChoicePanel<Object, Object> dropDownChoicePanel = new DropDownChoicePanel<>(
				new PropertyModel<Object>(modelObject, fieldName));
		dropDownChoicePanel.addField((Serializable) modelObject, fieldName, formFieldName, converter, choices);
		this.formFields.add(dropDownChoicePanel);

	}

	public void addMultipleChoice(Object modelObject, String fieldName, String formFieldName, List<Object> choices,
			AtomicLimesConverter<?> converter) {
		MultipleChoicePanel<Object, Object> multipleChoicePanel = new MultipleChoicePanel<>(
				new PropertyModel<Object>(modelObject, fieldName));
		multipleChoicePanel.addField((Serializable) modelObject, fieldName, formFieldName, converter, choices);
		this.formFields.add(multipleChoicePanel);
	}

	private class FormFieldListView extends ListView<Panel> {

		private static final long serialVersionUID = 1L;

		public FormFieldListView(String id, List<Panel> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<Panel> item) {
			Panel panel = item.getModelObject();
			item.add(panel);
		}

	}

}
