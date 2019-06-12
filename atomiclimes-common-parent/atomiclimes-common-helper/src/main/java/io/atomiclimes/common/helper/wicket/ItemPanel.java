package io.atomiclimes.common.helper.wicket;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;


public class ItemPanel<T, C> extends Panel {

	private static final long serialVersionUID = 1L;
	private Form<T> form;
	private List<LabeledTextField> textFields = new LinkedList<>();

	public ItemPanel(String id, IModel<T> model) {
		super(id, model);
		IModel<T> compound = new CompoundPropertyModel<>(model);
		form = new Form<>("form", compound);
		form.add(new TextFieldListView("textFields", this.textFields));
		this.add(form);
	}

	public void addTextField(Object modelObject, String fieldName, String formFieldName,
			AtomicLimesConverter<?> converter) {
		
		this.textFields.add(new LabeledTextField("field", new PropertyModel<>(modelObject, fieldName), formFieldName) {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "unchecked", "hiding" })
			@Override
			public <C> IConverter<C> getConverter(Class<C> type) {
				if (converter != null && type == getConverterType()) {
					return (IConverter<C>) converter;
				} else {
					return super.getConverter(type);
				}
			}

			@SuppressWarnings("unchecked")
			private Class<C> getConverterType() {
				return (Class<C>) ((ParameterizedType) (converter.getClass().getGenericSuperclass()))
						.getActualTypeArguments()[0];
			}
		});

	}

	public void addMultiselect(String id, IModel<? extends Collection<C>> model, List<? extends C> choices) {
		this.form.add(new CheckBoxMultipleChoice<>(id, model, choices));
	}

	private class TextFieldListView extends ListView<LabeledTextField> {

		private static final long serialVersionUID = 1L;

		public TextFieldListView(String id, List<LabeledTextField> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<LabeledTextField> item) {
			LabeledTextField modelObject = item.getModelObject();
			Label formLabel = modelObject.getFormLabel();
			modelObject.add(AttributeModifier.replace("id", modelObject.getFormFieldName()));
			modelObject.add(AttributeModifier.replace("placeholder", "Enter " + modelObject.getFormFieldName()));
			item.add(formLabel);
			item.add(modelObject);
		}

	}

}
