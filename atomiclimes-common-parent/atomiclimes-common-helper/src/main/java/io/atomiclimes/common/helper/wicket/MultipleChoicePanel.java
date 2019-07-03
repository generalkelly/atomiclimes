package io.atomiclimes.common.helper.wicket;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class MultipleChoicePanel<T, C> extends Panel {

	private static final long serialVersionUID = 1L;
	private Form<T> form;
	private AtomicLimesConverter<?> converter;
	private Serializable modelObject;
	private String fieldName;
	private String formFieldName;
	private List<T> choices;

	public MultipleChoicePanel(IModel<T> model) {
		super("field", model);
		this.form = new Form<>("form", model);
		this.add(form);
	}

	public void addField(Serializable modelObject, String fieldName, String formFieldName,
			AtomicLimesConverter<?> converter, List<T> choices) {
		this.modelObject = modelObject;
		this.fieldName = fieldName;
		this.formFieldName = formFieldName;
		this.converter = converter;
		this.choices = choices;

		Label formLabel = new Label("label", formFieldName);
		formLabel.add(AttributeModifier.replace("for", formFieldName));
		this.form.add(formLabel);
		this.form.add(generateField());

	}

	private FormComponent<Collection<T>> generateField() {
		ListMultipleChoice<T> multipleChoice = new ListMultipleChoice<T>("multipleChoice",
				new PropertyModel<>(modelObject, fieldName), choices, new IChoiceRenderer<T>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Object getDisplayValue(T object) {
						return object.toString();
					}

					@Override
					public String getIdValue(T object, int index) {
						return object.toString();
					}

					@Override
					public T getObject(String id, IModel<? extends List<? extends T>> choices) {
						List<? extends T> choicesList = choices.getObject();
						for(T choice : choicesList) {
							if (choice.toString().equals(id)) {
								return choice;
							}
						}
						return null;
					}
				}) {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "hiding", "unchecked" })
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

		};
		multipleChoice.add(AttributeModifier.replace("id", formFieldName));
		multipleChoice.add(AttributeModifier.replace("placeholder", "Enter " + formFieldName));

		return multipleChoice;
	}

}
