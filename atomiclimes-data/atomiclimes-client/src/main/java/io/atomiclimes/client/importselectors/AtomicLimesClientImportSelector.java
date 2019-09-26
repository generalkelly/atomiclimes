package io.atomiclimes.client.importselectors;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

public class AtomicLimesClientImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(
				importingClassMetadata.getAnnotationAttributes(EnableAtomicLimesClient.class.getName(), false));

		ClientType type = (ClientType) attributes.get("type");
		if (type == ClientType.DEFAULT) {
			return new String[] { "io.atomiclimes.client.configuration.AtomicLimesClientConfiguration",
					"io.atomiclimes.client.configuration.DefaultConfig" };
		}
		if (type == ClientType.AGENT) {
			return new String[] { "io.atomiclimes.client.configuration.AtomicLimesClientConfiguration",
					"io.atomiclimes.client.configuration.AgentConfig" };
		} else {
			return new String[] {};
		}
	}

}
