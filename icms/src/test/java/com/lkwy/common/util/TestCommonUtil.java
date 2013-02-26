package com.lkwy.common.util;

import junit.framework.Assert;

import org.junit.Test;

import com.lkwy.common.util.CommonUtil;

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
	
	

}
