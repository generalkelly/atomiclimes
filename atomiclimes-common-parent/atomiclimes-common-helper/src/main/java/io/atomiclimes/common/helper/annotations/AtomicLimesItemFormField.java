package io.atomiclimes.common.helper.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.atomiclimes.common.helper.enums.AtomicLimesFormInputType;
import io.atomiclimes.common.helper.wicket.converter.AtomicLimesConverter;

@Retention(RUNTIME)
@Target(FIELD)
public @interface AtomicLimesItemFormField {

	String fieldName();

	AtomicLimesFormInputType fieldType() default AtomicLimesFormInputType.TEXTFIELD;

	@SuppressWarnings("rawtypes")
	public Class<? extends AtomicLimesConverter> using() default AtomicLimesConverter.None.class;

}
