package com.lkwy.stock.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.common.AbstractTest;
import com.lkwy.stock.entity.StockCheck;

public class TestStockCheckService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestStockCheckService.class);
	
	@Autowired
	StockCheckService stockCheckService;
	
	@Test
	public void testrecalculateStockBringForwardForAllItem(){
		
		stockCheckService.recalculateStockBringForwardForAllItem(5, 2013);
		
		List<StockCheck> list = stockCheckService.getStockCheckListForItem("cda7a876-f737-413d-b434-511a92c08509", 4, 2013);
		
		for(StockCheck sc: list){
			LOGGER.debug("{} - {}|{}", new Object[]{sc.getItem().getName(), sc.getType(), sc.getUnit()});
		}
		
	}
	
	@Test
	public void testgetStockCheckListForItemOnDateRange(){
		List<StockCheck> stockCheckList = stockCheckService.getStockCheckListForItemOnDateRange("a6585d72-f984-4bc2-8092-058af3436d3e", 4, 2013, null, null);
		
		LOGGER.debug("size: {}", stockCheckList.size());
		if(stockCheckList.size() > 0){
			for(StockCheck sc: stockCheckList){
				LOGGER.debug("{} - {} - {} - {}", new Object[]{sc.getDate(), sc.getDescription(), sc.getUnit(), sc.getType()});
			}
		}
	}
	
	@Test
	public void testgetStockBalanceForItem(){
		long balance = stockCheckService.getStockBalanceForItem("a6585d72-f984-4bc2-8092-058af3436d3e", 4, 2013);
		LOGGER.debug("balance: {}", balance);
	}
	
	@Test
	public void getStockCheck(){
		
		List<StockCheck> stockCheckList = stockCheckService.getStockCheckListForItem("a6585d72-f984-4bc2-8092-058af3436d3e", 4, 2013);
		
		LOGGER.debug("size: {}", stockCheckList.size());
		if(stockCheckList.size() > 0){
			for(StockCheck sc: stockCheckList){
				LOGGER.debug("{} - {} - {} - {}", new Object[]{sc.getItem().getName(), sc.getDate(), sc.getUnit(), sc.getType()});
			}
		}
	}

}
