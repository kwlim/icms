package com.lkwy.sample.data.service;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.sample.data.entity.SampleEntityObject;
import com.lkwy.sample.data.service.SampleEntityService;

public class TestSampleEntityService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestSampleEntityService.class);
	
	@Autowired
	private SampleEntityService service;
	
	@Test
	public void testCreate30RecordsIfNotExist(){
		service.create30RecordsIfNotExist();
		
		
	}
	
	@Test
	public void testAddEntity(){
		
		SampleEntityObject object = new SampleEntityObject("test name");
		object = service.saveEntity(object);
		
		SampleEntityObject object2 = service.getEntityById(object.getId());
		
		Assert.assertEquals(object.getId(), object2.getId());
	}

}
