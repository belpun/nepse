package com.nepse.dao;

public interface IGenericRepository {
	public <E> E save(E entity);
}
