package com.lkwy.sample.data.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.sample.data.dao.ISampleEntityObjectRepository;
import com.lkwy.sample.data.entity.SampleEntityObject;

public class TestISampleEntityObjectRepository extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestISampleEntityObjectRepository.class);
	
	@Autowired
	ISampleEntityObjectRepository repo;
	
	@Test
	public void testFindByLikeName(){
		SampleEntityObject object = new SampleEntityObject("testName 1");
		repo.save(object);
		object = new SampleEntityObject("testName 2");	
		repo.save(object);
		
		List<SampleEntityObject> list = repo.findByLikeName("%testName%");
		LOGGER.debug("list size: {}", list.size());
		if(list != null && list.size() > 0){
			
			for(SampleEntityObject entity: list){
				LOGGER.debug("   entity: {}", entity.getName());
			}
			
		}
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}
	
	@Test
	public void testFindByName(){
		SampleEntityObject object = new SampleEntityObject("testName");
		repo.save(object);
		
		SampleEntityObject object2 = repo.findByName("testName");
		Assert.assertNotNull(object2);
	}
	
	@Test
	public void testAddEntity(){
		SampleEntityObject object = new SampleEntityObject("test");
		object = repo.save(object);
		
		SampleEntityObject object2 = repo.findOne(object.getId());
		Assert.assertEquals(object.getId(), object2.getId());
		
	}
	

}
