package com.lkwy.item.service;

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
import com.lkwy.item.entity.Item;

public class TestItemService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestItemService.class);
	
	@Autowired
	ItemService itemService;
	
	@Test
	public void testGetItemByCategoryIdBrandId(){
		Page<Item> list = itemService.getItemByCategoryIdBrandId("2b68197d-da4f-450f-b112-df820b2a3d09", null);
		LOGGER.debug("list size: {}", list.getTotalElements());
	}
	
	@Test
	public void testGetItemByNameCodeCategoryId(){
		
		Pageable pageable = new PageRequest(0, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "name");
		Page<Item> list = itemService.getItemByNameCodeCategoryId("", "", "", pageable);
		
		LOGGER.debug("list size: {}", list.getTotalElements());
		
		
	}

}
