package com.nepse.dao;

import java.io.Serializable;

public interface IGenericRepository {
	public <E> void save(E entity);
	
	public <E> E findById(Class<E> objectClazz, Serializable key);
}
