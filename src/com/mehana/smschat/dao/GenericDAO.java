package com.mehana.smschat.dao;

import java.util.Collection;

import com.mehana.smschat.exception.CommonException;
import com.mehana.smschat.model.common.AbstractEntity;


public interface GenericDAO<T extends AbstractEntity> {

	Collection<T> all();

	T loadById(Long id);

	void remove(T entity);

	T save(T entity) throws CommonException;

}
