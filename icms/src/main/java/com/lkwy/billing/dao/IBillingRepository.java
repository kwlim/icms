package com.lkwy.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.billing.entity.Billing;

public interface IBillingRepository extends JpaRepository<Billing, String>{
	
	public Billing findOneByBillingKey(String billingKey);

}
