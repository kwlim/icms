package com.lkwy.billing.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.billing.entity.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, String>{
	
	public Page<Payment> findByBilling_billingKey(String billingKey, Pageable pageable);
	
	
}
