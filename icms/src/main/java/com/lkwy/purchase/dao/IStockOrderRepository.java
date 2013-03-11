package com.lkwy.purchase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.purchase.entity.StockOrder;

public interface IStockOrderRepository extends JpaRepository<StockOrder, String>{

	
	
}