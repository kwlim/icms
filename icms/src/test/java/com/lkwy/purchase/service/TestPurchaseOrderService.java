package com.lkwy.purchase.service;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lkwy.common.AbstractTest;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.purchase.entity.PurchaseOrder;

public class TestPurchaseOrderService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestPurchaseOrderService.class);
	
	@Autowired
	PurchaseOrderService poService;
	
	@Test
	public void testGetPoByPoNumberDateVendor(){
		Pageable pageable = new PageRequest(0, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "poNumber");
		Page<PurchaseOrder> result = poService.getPoByPoNumberDateVendor("something", new Date(), new Date(), "something", pageable);
		
		LOGGER.debug("result.size={}", result.getContent());
		
	}

}
