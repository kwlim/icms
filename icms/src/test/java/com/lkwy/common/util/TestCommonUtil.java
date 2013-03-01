package com.lkwy.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class TestCommonUtil {
	
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
	public void something() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = sdf.parse("01-05-2013");
		System.out.println("date = " + date);
	}
	

}
