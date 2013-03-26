package com.lkwy.job.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.job.entity.JobOrder;

public interface IJobOrderRepository extends JpaRepository<JobOrder, String>{
	
	
}
