package com.lkwy.purchase.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lkwy.item.entity.Item;
import com.lkwy.purchase.entity.StockOrder;

public interface IStockOrderRepository extends JpaRepository<StockOrder, String>{
	
	public List<StockOrder> findByItem_Id(String id, Pageable pageable);
	
	@Query("SELECT DISTINCT so.item FROM StockOrder so WHERE so.purchaseOrder.poDate >= ?1 AND so.purchaseOrder.poDate <= ?2 ")
	public List<Item> findDistinctStockOrderItemDateRange(Date dateFrom, Date dateTo);
	
	@Query("SELECT SUM(so.quantity) FROM StockOrder so WHERE so.item.id = ?1 AND so.purchaseOrder.poDate >= ?2 AND so.purchaseOrder.poDate <= ?3 ")
	public Long sumByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo);
	
	@Query("SELECT so FROM StockOrder so WHERE so.item.id = ?1 AND so.purchaseOrder.poDate >= ?2 AND so.purchaseOrder.poDate <= ?3 ")
	public List<StockOrder> findByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo);
	
}