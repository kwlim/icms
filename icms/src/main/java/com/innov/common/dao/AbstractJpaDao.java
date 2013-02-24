package com.innov.common.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

public abstract class AbstractJpaDao<T extends Serializable> implements IAbstractDao<T>{
	
	private Class<T> clazz;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public abstract void initClazz();
	
	public AbstractJpaDao() {
		initClazz();
	}
	
	protected void setClazz(final Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Integer findAllCount() {
		String selectStatement = constructSelectCountStatement(null, null, null);
        Query query = entityManager.createQuery(selectStatement);
        
        int count = ((Long)query.getResultList().iterator().next()).intValue();
        return count;
	}
	
	@Override
	public List<T> findAll() {
		String selectStatement = constructSelectStatement(null, null, null);
	    Query query = entityManager.createQuery(selectStatement);
	    
	    return (List<T>)query.getResultList();
	}
	
	@Override
	public List<T> findBy(final String conditions, final Object[] params, final String sort, final Boolean desc, final Integer start, final Integer rows){
		
		Preconditions.checkNotNull(conditions, "Conditions are null");
		Preconditions.checkNotNull(params, "Params are null");
		Preconditions.checkNotNull(sort, "Sort is null");
        Preconditions.checkNotNull(start, "Start is null");
        Preconditions.checkNotNull(rows, "Rows is null");
		Preconditions.checkArgument(start >= 0, "start must not be negative");
		Preconditions.checkArgument(rows > 0, "rows must not be negative");
		
		String selectStatement = constructSelectStatement(conditions, sort, desc);
		Query query = entityManager.createQuery(selectStatement);
		
		insertParamsToQuery(query, params);
		setQueryLimits(query, start, rows);
		
		return (List<T>)query.getResultList();
	}
	
	@Override
	public Integer findCountBy(final String conditions, final Object[] params){
		
		Preconditions.checkNotNull(conditions, "Conditions are null");
		Preconditions.checkNotNull(params, "Params are null");
		
		String selectStatement = constructSelectCountStatement(conditions, null, null);
		Query query = entityManager.createQuery(selectStatement);
		
		insertParamsToQuery(query, params);
		
		int count = ((Long)query.getResultList().iterator().next()).intValue();
		return count;
	}
	
	private String constructSelectCountStatement(final String conditions, final String sort, final Boolean desc){
		StringBuilder selectStatement = new StringBuilder("SELECT COUNT(*) ");
		selectStatement.append(constructSelectStatement(conditions, sort, desc));
		return selectStatement.toString();
	}
	
	private String constructSelectStatement(final String conditions, final String sort, final Boolean desc){
		StringBuilder selectStatement = new StringBuilder("FROM ");
		selectStatement.append("User").append(" ");
		
		if(!StringUtils.isEmpty(conditions)){
			selectStatement.append(" WHERE ").append(conditions);
		}
		
		if(!StringUtils.isEmpty(sort)){
			selectStatement.append(" ORDER BY ").append(sort);
			
			if(desc != null && desc){
				selectStatement.append(" DEC");
			}
		}
		
		return selectStatement.toString();
	}
	
	private void insertParamsToQuery(Query query, Object[] params){
		if(params != null && params.length > 0){
			for(int i=0; i< params.length; i++){
				query.setParameter(i+1, params[i]);
			}
		}
	}

	private void setQueryLimits(Query query, final Integer start, final Integer rows){
		if(start != null){
			query.setFirstResult(start);
		}else{
			query.setFirstResult(0);
		}
		
		if(rows != null){
			query.setMaxResults(rows);
		}
	}
	
	@Override
	public T findById(final String id){
		Preconditions.checkArgument(id != null);
		return (T)entityManager.find(clazz, id);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void add(T entity){
		Preconditions.checkNotNull(entity);
		entityManager.persist(entity);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void update(T entity){
		Preconditions.checkNotNull(entity);
		entityManager.merge(entity);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void delete(T entity){
		Preconditions.checkNotNull(entity);
		entityManager.remove(entity);
	}
	
	@Override
	public void deleteById(final String id){
		Preconditions.checkNotNull(id);
		T entity = findById(id);
		delete(entity);
	}
	
}
