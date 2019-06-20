package io.atomiclimes.common.helper.wicket;

import java.lang.reflect.ParameterizedType;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class TextFieldPanel<T, C> extends Panel {

	private static final long serialVersionUID = 1L;
	private Form<T> form;
	private AtomicLimesConverter<?> converter;
	private Object modelObject;
	private String fieldName;
	private String formFieldName;

	public TextFieldPanel(IModel<T> model) {
		super("field", model);
		this.form = new Form<>("form", model);
		this.add(form);
	}

	public void addField(Object modelObject, String fieldName, String formFieldName,
			AtomicLimesConverter<?> converter) {
		this.modelObject = modelObject;
		this.fieldName = fieldName;
		this.formFieldName = formFieldName;
		this.converter = converter;

		Label formLabel = new Label("label", formFieldName);
		formLabel.add(AttributeModifier.replace("for", formFieldName));
		this.form.add(formLabel);
		this.form.add(generateField());

	}

	@SuppressWarnings("unchecked")
	private FormComponent<T> generateField() {
		TextField<T> textField = new TextField<T>("textField", new PropertyModel<>(modelObject, fieldName)) {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "hiding" })
			@Override
			public <C> IConverter<C> getConverter(Class<C> type) {
				if (converter != null && type == getConverterType()) {
					return (IConverter<C>) converter;
				} else {
					return super.getConverter(type);
				}
			}

			private Class<C> getConverterType() {
				return (Class<C>) ((ParameterizedType) (converter.getClass().getGenericSuperclass()))
						.getActualTypeArguments()[0];
			}
		};

		textField.add(AttributeModifier.replace("id", formFieldName));
		textField.add(AttributeModifier.replace("placeholder", "Enter " + formFieldName));

		return textField;
	}

}
