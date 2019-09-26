package io.atomiclimes.data.service.importselectors;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class AtomicLimesMasterImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] { "io.atomiclimes.data.service.master.configuration.AtomicLimesMasterConfiguration" };
	}

}
