package com.lkwy.sample.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lkwy.sample.data.entity.SampleEntityObject;

public interface ISampleEntityObjectRepository extends CrudRepository<SampleEntityObject, String>{
	
	public SampleEntityObject findByName(String name);
	
	@Query("FROM SampleEntityObject WHERE name LIKE ?1")
	public List<SampleEntityObject> findByLikeName(String name);
	
}
