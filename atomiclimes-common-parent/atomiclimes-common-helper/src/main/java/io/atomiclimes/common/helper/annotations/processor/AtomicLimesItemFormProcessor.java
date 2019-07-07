package io.atomiclimes.common.helper.annotations.processor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;

import org.apache.wicket.model.IModel;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;

import io.atomiclimes.common.helper.annotations.AtomicLimesItemForm;
import io.atomiclimes.common.helper.annotations.AtomicLimesItemFormField;
import io.atomiclimes.common.helper.enums.AtomicLimesFormInputType;
import io.atomiclimes.common.helper.exceptions.AtomicLimesItemFormProcessingException;
import io.atomiclimes.common.helper.wicket.ItemPanel;
import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

public class AtomicLimesItemFormProcessor<I> {

	private IModel<I> model;
	private ApplicationContext applicationContext;

	public AtomicLimesItemFormProcessor(IModel<I> model, ApplicationContext applicationContext) {
		this.model = model;
		this.applicationContext = applicationContext;
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
				Class<?> typeOfCurrentField = field.getType();
				AtomicLimesFormInputType fieldType = formFieldAnnotation.fieldType();
				AtomicLimesConverter<?> converter = getConverter(formFieldAnnotation);
				if (fieldType == AtomicLimesFormInputType.TEXTFIELD) {
					panel.addTextField(this.model.getObject(), fieldName, formFieldName, converter);
				} else if (fieldType == AtomicLimesFormInputType.DROPDOWN_CHOICE) {
					if (typeOfCurrentField.isEnum()) {
						addDropDownChoiceForEnumToPanel(panel, formFieldName, fieldName, converter, typeOfCurrentField);
					} else if (typeOfCurrentField.isAnnotationPresent(Entity.class)) {
						addDropDownChoiceForEntityToPanel(panel, formFieldName, fieldName, converter,
								typeOfCurrentField);
					}
				} else if (fieldType == AtomicLimesFormInputType.MULTIPLE_CHOICE) {
//					TODO check, that the field type is actually a List
					Class<?> genericListType = ((Class<?>) ((ParameterizedType) field.getGenericType())
							.getActualTypeArguments()[0]);
					List<Object> items = getAllItemsForGivenEntityFromRepository(genericListType);
					panel.addMultipleChoice(this.model.getObject(), fieldName, formFieldName, items, converter);
				}
			}
		}
		return panel;
	}

	private void addDropDownChoiceForEnumToPanel(ItemPanel<I> panel, String formFieldName, String fieldName,
			AtomicLimesConverter<?> converter, Class<?> typeOfCurrentField) {
		List<Object> enumList = Arrays.asList(typeOfCurrentField.getEnumConstants());
		panel.addDropdownChoice(this.model.getObject(), fieldName, formFieldName, enumList, converter);
	}

	private void addDropDownChoiceForEntityToPanel(ItemPanel<I> panel, String formFieldName, String fieldName,
			AtomicLimesConverter<?> converter, Class<?> typeOfCurrentField) {
		List<Object> items = getAllItemsForGivenEntityFromRepository(typeOfCurrentField);
		panel.addDropdownChoice(this.model.getObject(), fieldName, formFieldName, items, converter);
	}

	private List<Object> getAllItemsForGivenEntityFromRepository(Class<?> typeOfCurrentField) {
		CrudRepository<?, Long> repository = getRepositoryForEntityClass(typeOfCurrentField);
		List<Object> items = new LinkedList<>();
		Iterable<?> iterableFromRepo = null;
		if (repository != null) {
			iterableFromRepo = repository.findAll();
		}
		if (iterableFromRepo != null) {
			iterableFromRepo.forEach(items::add);
		}
		return items;
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

	@SuppressWarnings("unchecked")
	private <C> CrudRepository<C, Long> getRepositoryForEntityClass(Class<C> entityClass) {
		Optional<Object> optionalRepository = new Repositories(applicationContext).getRepositoryFor(entityClass);
		CrudRepository<C, Long> repository = null;
		if (optionalRepository.isPresent()) {
			repository = (CrudRepository<C, Long>) optionalRepository.get();
		}
		return repository;
	}

}
