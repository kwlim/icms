package com.lkwy.job.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.job.dao.IJobItemRepository;
import com.lkwy.job.dao.IJobOrderRepository;
import com.lkwy.job.dao.JobOrderSpecifications;
import com.lkwy.job.entity.JobItem;
import com.lkwy.job.entity.JobOrder;

@Service
public class JobOrderService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(JobOrderService.class);
	
	@Autowired
	IJobOrderRepository joRepo;
	
	@Autowired
	IJobItemRepository jobItemRepo;
	
	public Page<JobOrder> getJobOrderByJobNumberCarPlateJobDateRange(String jobNumberOrCarPlate, Date dateFrom, Date dateTo, Pageable pageable){
		String wildCardJobNumberOrCarPlate = CommonUtil.addWildCard(jobNumberOrCarPlate);
		Date processedDateFrom = CommonUtil.convertDateAsStartDate(dateFrom);
		Date processedDateTo = CommonUtil.convertDateAsEndDate(dateTo);
		
		return joRepo.findAll(JobOrderSpecifications.byLikePoNumberPoDateVendorId(wildCardJobNumberOrCarPlate, processedDateFrom, processedDateTo), pageable);
	}
	
	public void deleteJobItem(String soId){
		jobItemRepo.delete(soId);
	}
	
	public JobItem saveJobItem(JobItem so){
		return jobItemRepo.save(so);
	}
	
	public JobOrder saveJob(JobOrder jo){
		return joRepo.save(jo);
	}
	
	public void deleteJob(String id){
		joRepo.delete(id); 
	}
	
	public JobOrder getJobById(String id){
		return joRepo.findOne(id);
	}

}
