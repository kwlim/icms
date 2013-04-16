package com.lkwy.job.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lkwy.common.AbstractTest;
import com.lkwy.common.util.CommonUtil;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.item.entity.Item;
import com.lkwy.job.entity.JobOrder;
import com.lkwy.job.entity.JobPriceCategory;

public class TestJobOrderService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestJobOrderService.class);
	
	@Autowired
	JobOrderService jobService;
	
	@Test
	public void getSumPriceMarkupLabourByItemCategory(){
		
		DateTime dateFrom = new DateTime(2013, 4, 7, 0, 0);
		DateTime dateTo = new DateTime(2013, 4, 7, 23, 59);
		
		List<JobPriceCategory> list = jobService.getSumPriceMarkupLabourByItemCategory(dateFrom.toDate(), dateTo.toDate());
		LOGGER.debug("list size = {}", list.size());
		
		for(JobPriceCategory temp: list){
			LOGGER.debug("cat = {}|{}|{}", new Object[]{temp.getCategory().getName(), temp.getPrice(), temp.getEarning()});
		}
		
	}
	
	@Test
	public void getSumMarkupLabourByItemCategory(){
		
		DateTime date = new DateTime(2013, 4, 7, 0, 0);
		
		List<JobPriceCategory> list = jobService.getSumMarkupLabourByItemCategory(date.toDate());
		LOGGER.debug("list size = {}", list.size());
		
		for(JobPriceCategory temp: list){
			LOGGER.debug("cat = {}|{}", temp.getCategory().getName(), temp.getPrice());
		}
		
	}
	
	@Test
	public void testgetSumPriceByItemCategory(){
		
		DateTime date = new DateTime(2013, 4, 7, 0, 0);
		
		List<JobPriceCategory> list = jobService.getSumPriceByItemCategory(date.toDate());
		LOGGER.debug("list size = {}", list.size());
		
		for(JobPriceCategory temp: list){
			LOGGER.debug("cat = {}|{}", temp.getCategory().getName(), temp.getPrice());
		}
		
	}
	
	@Test
	public void testgetSumMarkupLabourPrice() {
		
		DateTime date = new DateTime(2013, 4, 7, 0, 0);
		
		Double sum = jobService.getSumMarkupLabourPrice(date.toDate());
		LOGGER.debug("{}|sum={}", date, sum);
		
	}
	
	@Test
	public void testgetSumMarkupPrice() {
		
		DateTime date = new DateTime(2013, 4, 7, 0, 0);
		
		Double sum = jobService.getSumMarkupPrice(date.toDate());
		LOGGER.debug("{}|sum={}", date, sum);
		
	}
	
	@Test
	public void testgetSumJomPrice() {
		
		DateTime date = new DateTime(2013, 4, 7, 0, 0);
		
		Double sum = jobService.getSumJobPrice(date.toDate());
		LOGGER.debug("{}|sum={}", date, sum);
		
	}
	
	@Test
	public void testgetDistinctJobOrderItemAndMonthYear(){
		
		List<Item> list = jobService.getDistinctJobOrderItemAndMonthYear(4, 2013);
		LOGGER.debug("list size = {}", list.size());
		
		for(Item item: list){
			LOGGER.debug("item = {}", item.getName());
		}
		
	}
	
	@Test
	public void testgetDistinctJobOrderItemAndDateRange(){
		DateTime firstDayOfMonth = new DateTime(2013, 4, 1, 0, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		Date lastDayOfMonthEndDate = CommonUtil.convertDateAsEndDate(lastDayOfMonth.toDate());
		
		List<Item> list = jobService.getDistinctJobOrderItemAndDateRange(firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		LOGGER.debug("list size = {}", list.size());
		
		for(Item item: list){
			LOGGER.debug("item = {}", item.getName());
		}
		
	}
	
	@Test
	public void testgetSumItemUnitByItemIdAndPoDateRange(){
		DateTime firstDayOfMonth = new DateTime(2013, 1, 1, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		Date lastDayOfMonthEndDate = CommonUtil.convertDateAsEndDate(lastDayOfMonth.toDate());
		Long sum = jobService.getSumItemUnitByItemIdAndPoDateRange("a6585d72-f984-4bc2-8092-058af3436d3e", firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		LOGGER.debug("sum = {}", sum);
	}
	
	@Test
	public void testGetJobOrderByJobNumberCarPlateJobDateRange(){
		Pageable pageable = new PageRequest(0, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "jobNumber");
		Page<JobOrder> result = jobService.getJobOrderByJobNumberCarPlateJobDateRange("", new Date(), new Date(), pageable);
		
		LOGGER.debug("result.size={}", result.getContent());
	}

}
