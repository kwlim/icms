package com.lkwy.job.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lkwy.job.entity.JobOrder;

public interface IJobOrderRepository extends JpaRepository<JobOrder, String>{
	
	@Query("SELECT SUM(jo.price) FROM JobOrder jo WHERE jo.jobDate >= ?1 AND jo.jobDate <= ?2")
	public Double sumByJobDateAndJobDate(Date dateFrom, Date dateTo);
	
	public Page<JobOrder> findAll(Specification<JobOrder> specs, Pageable pageable);
	
}
