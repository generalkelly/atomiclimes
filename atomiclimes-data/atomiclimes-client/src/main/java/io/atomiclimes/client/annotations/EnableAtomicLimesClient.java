package io.atomiclimes.client.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import io.atomiclimes.client.importselectors.AtomicLimesClientImportSelector;
import io.atomiclimes.date.service.client.enums.ClientType;

@Retention(RUNTIME)
@Target(TYPE)
@Import(value = { AtomicLimesClientImportSelector.class })
public @interface EnableAtomicLimesClient {

	ClientType type() default ClientType.DEFAULT;

}
