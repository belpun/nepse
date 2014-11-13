package com.nepse.dao;

public interface IGenericRepository {
	public <E> void save(E entity);
}
