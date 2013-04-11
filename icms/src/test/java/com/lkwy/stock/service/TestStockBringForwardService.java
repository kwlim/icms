package com.lkwy.stock.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.common.util.CommonUtil;
import com.lkwy.item.entity.Item;
import com.lkwy.stock.entity.StockBringForward;

public class TestStockBringForwardService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestStockBringForwardService.class);
	
	@Autowired
	StockBringForwardService stockBfService;
	
	@Test
	public void testgetDistinctStockBringForwardItemAndMonthYear(){
		
		List<Item> list = stockBfService.getDistinctStockBringForwardItemAndMonthYear(4, 2013);
		LOGGER.debug("list size = {}", list.size());
		
		for(Item item: list){
			LOGGER.debug("item = {}", item.getName());
		}
		
	}
	
	@Test
	public void testgetDistinctStockBringForwardItemAndDateRange(){
		DateTime firstDayOfMonth = new DateTime(2013, 4, 1, 0, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		Date lastDayOfMonthEndDate = CommonUtil.convertDateAsEndDate(lastDayOfMonth.toDate());
		
		List<Item> list = stockBfService.getDistinctStockBringForwardItemAndDateRange(firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		LOGGER.debug("list size = {}", list.size());
		
		for(Item item: list){
			LOGGER.debug("item = {}", item.getName());
		}
		
	}
	
	@Test
	public void testGetStockBringFowardCreateIfApplicable(){
		
		StockBringForward sbf = stockBfService.getStockBringFowardCreateIfApplicable(Months.ONE.getMonths(), 2013, "test");
		
		LOGGER.debug("{}", sbf);
	}

}
