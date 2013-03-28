package com.lkwy.purchase.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.purchase.entity.StockOrder;

public interface IStockOrderRepository extends JpaRepository<StockOrder, String>{
	
	public List<StockOrder> findByItem_Id(String id, Pageable pageable);
	
}