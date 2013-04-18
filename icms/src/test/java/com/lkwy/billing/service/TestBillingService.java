package com.lkwy.billing.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.billing.entity.Billing;
import com.lkwy.common.AbstractTest;

public class TestBillingService extends AbstractTest{
	
	@Autowired
	private BillingService billingService;
	
	@Test
	public void testgetBillingCreateIfNotExist(){
		
		Billing billing = billingService.getBillingCreateIfNotExist();
		
		Assert.assertEquals(BillingService.FIX_BILLING_KEY, billing.getBillingKey());
		
	}

}
