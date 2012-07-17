package com.mehana.smschat.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mehana.smschat.exception.CommonException;
import com.mehana.smschat.model.AbstractEntity;


public abstract class GenericDAOImpl<T extends AbstractEntity> implements GenericDAO<T> {

	protected final EntityManager manager;
	private final Class<T> aClass;

	protected GenericDAOImpl(EntityManager manager) {
		this.manager = manager;

		@SuppressWarnings("unchecked")
		Class<T> aClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		this.aClass = aClass;
	}

	public Collection<T> all() {
		Query query = manager.createQuery("from " + aClass.getName());

		@SuppressWarnings("unchecked")
		Collection<T> list = query.getResultList();

		return list;
	}

	public T find(Long id) {
		return manager.find(aClass, id);
	}

	public void remove(T entity) {
		manager.remove(manager.getReference(aClass, entity.getId()));
	}

	public T save(T entity) throws CommonException {
		return manager.merge(entity);
	}

}
