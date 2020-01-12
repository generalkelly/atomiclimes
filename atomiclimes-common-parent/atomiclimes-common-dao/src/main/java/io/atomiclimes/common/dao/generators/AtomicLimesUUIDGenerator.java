package io.atomiclimes.common.dao.generators;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class AtomicLimesUUIDGenerator extends UUIDGenerator {

	private String entity;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		entity = params.getProperty(ENTITY_NAME);
		super.configure(type, params, serviceRegistry);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) {
		Serializable id = s.getEntityPersister(this.entity, obj).getIdentifier(obj, s);
		if (id == null) {
			id = super.generate(s, obj);
		}
		return id;
	}
}