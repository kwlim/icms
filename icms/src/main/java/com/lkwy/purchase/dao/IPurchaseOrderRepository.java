package com.lkwy.purchase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.purchase.entity.PurchaseOrder;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, String>{
	
	
	
}
