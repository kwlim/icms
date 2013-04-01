package com.lkwy.job.service;

import java.util.Date;

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
import com.lkwy.job.entity.JobOrder;

public class TestJobOrderService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestJobOrderService.class);
	
	@Autowired
	JobOrderService jobService;
	
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
