package com.lkwy.stock.service;

import org.joda.time.Months;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.stock.entity.StockBringForward;

public class TestStockBringForwardService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestStockBringForwardService.class);
	
	@Autowired
	StockBringForwardService stockBfService;
	
	@Test
	public void testGetStockBringFowardCreateIfApplicable(){
		
		StockBringForward sbf = stockBfService.getStockBringFowardCreateIfApplicable(Months.ONE.getMonths(), 2013, "test");
		
		LOGGER.debug("{}", sbf);
	}

}
