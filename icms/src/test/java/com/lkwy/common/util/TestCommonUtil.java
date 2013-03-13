package com.lkwy.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCommonUtil {
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestCommonUtil.class);
	
	@Test
	public void testAddWildCard(){
		String name = null;
		String result = CommonUtil.addWildCard(name);
		
		Assert.assertEquals("%%", result);
		
		name = "";
		result = CommonUtil.addWildCard(name);
		Assert.assertEquals("%%", result);
		
		name = "test";
		result = CommonUtil.addWildCard(name);
		Assert.assertEquals("%test%", result);
	}
	
	@Test
	public void testConvertDateAsStartDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
		DateTime datetime = new DateTime(2013, 01, 30, 12, 30);
		LOGGER.debug("datetime: {}", datetime);
		Date date = CommonUtil.convertDateAsStartDate(datetime.toDate());
		LOGGER.debug("date: {}", date);
		String dateFormat = sdf.format(date);
		LOGGER.debug("dateFormat: {}", dateFormat);
		Assert.assertEquals("30-01-2013 12:00:00 AM", dateFormat);
	}
	
	@Test
	public void testConvertDateAsEndDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
		DateTime datetime = new DateTime(2013, 01, 30, 12, 30);
		LOGGER.debug("datetime: {}", datetime);
		Date date = CommonUtil.convertDateAsEndDate(datetime.toDate());
		LOGGER.debug("date: {}", date);
		String dateFormat = sdf.format(date);
		LOGGER.debug("dateFormat: {}", dateFormat);
		Assert.assertEquals("30-01-2013 11:59:59 PM", dateFormat);
	}

}
