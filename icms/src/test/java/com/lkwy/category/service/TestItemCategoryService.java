package com.lkwy.category.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lkwy.category.entity.ItemCategory;
import com.lkwy.common.AbstractTest;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.sample.data.dao.TestISampleEntityObjectRepository;

public class TestItemCategoryService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestISampleEntityObjectRepository.class);
	
	@Autowired
	ItemCategoryService itemCatService;
	
	@Test
	public void testGetCategoryByName(){
		
		Pageable pageable = new PageRequest(0, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "name");
		Page<ItemCategory> list = itemCatService.getCategoryByName("", pageable);
		LOGGER.debug("size: {}", list.getTotalElements());
		LOGGER.debug("page size: {}", list.getSize());
	}

}
