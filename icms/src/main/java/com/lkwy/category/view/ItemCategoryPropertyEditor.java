package com.lkwy.category.view;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkwy.category.entity.ItemCategory;

public class ItemCategoryPropertyEditor extends PropertyEditorSupport{
	
	static final Logger LOGGER = LoggerFactory.getLogger(ItemCategoryPropertyEditor.class);
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		if (StringUtils.isEmpty(text)) {
			LOGGER.debug("text is empty = {}", text);
			setValue(null);
		}else{
			LOGGER.debug("text = {}", text);
			ItemCategory category = new ItemCategory();
			category.setId(text);
			setValue(category);
		}
	}
	
}
