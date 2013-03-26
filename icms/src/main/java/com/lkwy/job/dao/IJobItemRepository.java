package com.lkwy.job.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.job.entity.JobItem;

public interface IJobItemRepository extends JpaRepository<JobItem, String>{

	
	
}