package io.atomiclimes.data.service.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import io.atomiclimes.data.service.importselectors.AtomicLimesMasterImportSelector;

@Retention(RUNTIME)
@Target(TYPE)
@Import(value = { AtomicLimesMasterImportSelector.class })
public @interface EnableAtomicLimesMaster {

}
