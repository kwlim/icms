package com.lkwy.purchase.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.purchase.entity.PurchaseOrder;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, String>{
	
	Page<PurchaseOrder> findAll(Specification<PurchaseOrder> specification, Pageable pageable);
	
}
