package com.lkwy.job.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.job.entity.JobOrder;

public interface IJobOrderRepository extends JpaRepository<JobOrder, String>{
	
	public Page<JobOrder> findAll(Specification<JobOrder> specs, Pageable pageable);
	
}
