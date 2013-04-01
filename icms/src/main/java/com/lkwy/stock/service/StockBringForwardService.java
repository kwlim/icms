package com.lkwy.stock.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.stock.dao.IStockBringForwardRepository;
import com.lkwy.stock.entity.StockBringForward;

@Service
public class StockBringForwardService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(StockBringForwardService.class);
	
	@Autowired
	IStockBringForwardRepository stockBfRepo;
	
	public StockBringForward saveStockBringForward(StockBringForward stockBringForward){
		return stockBfRepo.save(stockBringForward);
	}
	
	public StockBringForward getStockBringFowardCreateIfApplicable(int month, int year, String itemId){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		
		return  stockBfRepo.findByItemAndBfDateRange(firstDayOfMonth.toDate(), CommonUtil.convertDateAsEndDate(lastDayOfMonth.toDate()), itemId);
	}
	
}
