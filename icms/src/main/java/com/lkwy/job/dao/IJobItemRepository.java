package com.lkwy.job.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lkwy.item.entity.Item;
import com.lkwy.job.entity.JobItem;
import com.lkwy.job.entity.JobPriceCategory;

public interface IJobItemRepository extends JpaRepository<JobItem, String>{
	
	@Query("SELECT new com.lkwy.job.entity.JobPriceCategory(ji.item.category, " +
			"SUM((COALESCE(ji.unit, 0) * COALESCE(ji.stockPrice, 0)) + COALESCE(ji.markup, 0) + COALESCE(ji.labour, 0)), " +
			"SUM(COALESCE(ji.markup, 0)) + SUM(COALESCE(ji.labour, 0))) " +
			"FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 GROUP BY ji.item.category")
	public List<JobPriceCategory> sumPriceMarkupLabourByJobDateGroupByItemCategory(Date dateFrom, Date dateTo);
	
	@Query("SELECT new com.lkwy.job.entity.JobPriceCategory(ji.item.category, SUM(COALESCE(ji.markup, 0)) + SUM(COALESCE(ji.labour, 0))) " +
			"FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 GROUP BY ji.item.category")
	public List<JobPriceCategory> sumMarkupLabourByJobDateGroupByItemCategory(Date dateFrom, Date dateTo);
	
	@Query("SELECT new com.lkwy.job.entity.JobPriceCategory(ji.item.category, SUM(COALESCE(ji.jobOrder.price, 0))) " +
			"FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 GROUP BY ji.item.category")
	public List<JobPriceCategory> sumPriceByJobDateGroupByItemCategory(Date dateFrom, Date dateTo);
	
	@Query("SELECT SUM(COALESCE(ji.markup, 0)) + SUM(COALESCE(ji.labour, 0)) FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 ")
	public Double sumMarkupLabourByDateRange(Date dateFrom, Date dateTo);
	
	@Query("SELECT SUM(COALESCE(ji.markup, 0)) FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 ")
	public Double sumMarkupByDateRange(Date dateFrom, Date dateTo);
	
	@Query("SELECT DISTINCT ji.item FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 ")
	public List<Item> findDistinctJobItemDateRange(Date dateFrom, Date dateTo);
	
	@Query("SELECT SUM(COALESCE(ji.unit, 0)) FROM JobItem ji WHERE ji.item.id = ?1 AND ji.jobOrder.jobDate >= ?2 AND ji.jobOrder.jobDate <= ?3 ")
	public Long sumUnitByItemIdAndJobDateRange(String itemId, Date dateFrom, Date dateTo);
	
	@Query("SELECT ji FROM JobItem ji WHERE ji.item.id = ?1 AND ji.jobOrder.jobDate >= ?2 AND ji.jobOrder.jobDate <= ?3 ")
	public List<JobItem> findByItemIdAndJobDateRange(String itemId, Date dateFrom, Date dateTo);
	
}