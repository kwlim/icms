package com.lkwy.stock.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lkwy.item.entity.Item;
import com.lkwy.stock.entity.StockBringForward;

public interface IStockBringForwardRepository extends JpaRepository<StockBringForward, String>{
	
	@Query("SELECT DISTINCT sbf.item FROM StockBringForward sbf WHERE bfDate >= ?1 AND bfDate <= ?2 ")
	public List<Item> findDistinctJobItemDateRange(Date dateFrom, Date dateTo);
	
	@Query("SELECT sbf FROM StockBringForward sbf WHERE bfDate >= ?1 AND bfDate <= ?2 AND (sbf.item != null AND sbf.item.id = ?3)")
	public StockBringForward findByItemAndBfDateRange(Date dateFrom, Date dateTo, String itemId);

}
