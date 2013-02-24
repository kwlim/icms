package com.innov.common.util;

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
	
	

}
