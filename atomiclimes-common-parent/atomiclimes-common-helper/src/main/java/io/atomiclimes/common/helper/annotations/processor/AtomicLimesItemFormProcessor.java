package io.atomiclimes.common.helper.annotations.processor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import io.atomiclimes.common.helper.annotations.AtomicLimesItemForm;
import io.atomiclimes.common.helper.annotations.AtomicLimesItemFormField;
import io.atomiclimes.common.helper.enums.AtomicLimesFormInputType;
import io.atomiclimes.common.helper.exceptions.AtomicLimesItemFormProcessingException;
import io.atomiclimes.common.helper.wicket.ItemPanel;
import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class AtomicLimesItemFormProcessor<I> {

	private IModel<I> model;

	public AtomicLimesItemFormProcessor(IModel<I> model) {
		this.model = model;
		isItemForm(model.getObject());
	}

	private void isItemForm(Object object) {
		if (Objects.isNull(object)) {
			throw new AtomicLimesItemFormProcessingException("The object is null!");
		}

		Class<?> clazz = object.getClass();
		if (!clazz.isAnnotationPresent(AtomicLimesItemForm.class)) {
			throw new AtomicLimesItemFormProcessingException(
					"The class " + clazz.getSimpleName() + " is not annotated as AtomicLimesItemForm");
		}
	}

	public ItemPanel<I> getItemPanel(String id) {
		ItemPanel<I> panel = new ItemPanel<>(id, this.model);
		Class<?> clazz = this.model.getObject().getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(AtomicLimesItemFormField.class)) {
				AtomicLimesItemFormField formFieldAnnotation = field.getAnnotation(AtomicLimesItemFormField.class);
				String formFieldName = formFieldAnnotation.fieldName();
				String fieldName = field.getName();
				
				AtomicLimesFormInputType fieldType = formFieldAnnotation.fieldType();

				AtomicLimesConverter<?> converter = getConverter(formFieldAnnotation);

				if (fieldType == AtomicLimesFormInputType.TEXTFIELD) {
					panel.addTextField(this.model.getObject(), fieldName, formFieldName, converter);
				} else if (fieldType == AtomicLimesFormInputType.DROPDOWN_CHOICE) {
					Class<?> typeOfCurrentField = field.getType();
					if (typeOfCurrentField.isEnum()) {
						List<Object> enumList = Arrays.asList(typeOfCurrentField.getEnumConstants());
						panel.addDropdownChoice(this.model.getObject(), fieldName, formFieldName, enumList, converter);
					}
				}

			}
		}
		return panel;
	}

	private AtomicLimesConverter<?> getConverter(AtomicLimesItemFormField formFieldAnnotation) {
		AtomicLimesConverter<?> converter = null;
		if (!formFieldAnnotation.using().equals(AtomicLimesConverter.None.class)) {
			try {
				converter = formFieldAnnotation.using().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new AtomicLimesItemFormProcessingException("Could not create an instance of Deserializer");
			}
		}
		return converter;
	}

}
