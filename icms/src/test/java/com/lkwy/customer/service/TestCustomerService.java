package com.lkwy.customer.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.customer.entity.Customer;

public class TestCustomerService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestCustomerService.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testgetCustomerByCarPlateNumber(){
		Customer customer = customerService.getCustomerByCarPlateNumber("NCD354");
		LOGGER.debug("customer: {}", customer);
	}
	
	@Test
	public void testgetCustomerByLikeCarPlateNumber(){
		
		List<Customer> list = customerService.getCustomerByLikeCarPlateNumber("NCE");
		LOGGER.debug("size: {}", list.size());
	}

}
