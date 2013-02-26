package com.lkwy.common.dao;

import java.io.Serializable;
import java.util.List;

public interface IAbstractDao<T extends Serializable> {
	
	public List<T> findAll();
	public Integer findAllCount();
	public List<T> findBy(final String conditions, final Object[] params, final String sort, final Boolean desc, final Integer start, final Integer rows);
	public Integer findCountBy(final String conditions, final Object[] params);
	public T findById(final String id);
	public void add(T entity);
	public void update(T entity);
	public void delete(T entity);
	public void deleteById(final String id);
	

}
