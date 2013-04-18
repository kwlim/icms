package com.lkwy.company.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.company.entity.Company;

public class TestCompanyService extends AbstractTest{
	
	@Autowired
	private CompanyService comService;
	
	@Test
	public void testgetCompanyByKeyCreateIfNotExist(){
		
		Company com = comService.getCompanyCreateIfNotExist();
		
		Assert.assertEquals(CompanyService.FIX_COM_KEY, com.getCompanyKey());
		
	}

}
