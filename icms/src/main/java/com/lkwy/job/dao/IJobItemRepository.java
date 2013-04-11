package com.lkwy.job.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lkwy.item.entity.Item;
import com.lkwy.job.entity.JobItem;

public interface IJobItemRepository extends JpaRepository<JobItem, String>{
	
	@Query("SELECT DISTINCT ji.item FROM JobItem ji WHERE ji.jobOrder.jobDate >= ?1 AND ji.jobOrder.jobDate <= ?2 ")
	public List<Item> findDistinctJobItemDateRange(Date dateFrom, Date dateTo);
	
	@Query("SELECT SUM(ji.unit) FROM JobItem ji WHERE ji.item.id = ?1 AND ji.jobOrder.jobDate >= ?2 AND ji.jobOrder.jobDate <= ?3 ")
	public Long sumByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo);
	
	@Query("SELECT ji FROM JobItem ji WHERE ji.item.id = ?1 AND ji.jobOrder.jobDate >= ?2 AND ji.jobOrder.jobDate <= ?3 ")
	public List<JobItem> findByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo);
	
}