package com.lkwy.job.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkwy.job.dao.IJobItemRepository;
import com.lkwy.job.dao.IJobOrderRepository;
import com.lkwy.job.entity.JobItem;
import com.lkwy.job.entity.JobOrder;

@Service
public class JobOrderService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(JobOrderService.class);
	
	@Autowired
	IJobOrderRepository joRepo;
	
	@Autowired
	IJobItemRepository jobItemRepo;
	
	public void deleteJobItem(String soId){
		jobItemRepo.delete(soId);
	}
	
	public JobItem saveJobItem(JobItem so){
		return jobItemRepo.save(so);
	}
	
	public JobOrder saveJo(JobOrder jo){
		return joRepo.save(jo);
	}
	
	public void deleteJo(String id){
		joRepo.delete(id); 
	}
	
	public JobOrder getJoById(String id){
		return joRepo.findOne(id);
	}

}
