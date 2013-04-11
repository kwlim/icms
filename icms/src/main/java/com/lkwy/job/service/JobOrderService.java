package com.lkwy.job.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lkwy.code.service.CodeTrackService;
import com.lkwy.common.util.CommonUtil;
import com.lkwy.item.entity.Item;
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
	
	@Autowired
	CodeTrackService codeTrackService;
	
	public List<Item> getDistinctJobOrderItemAndMonthYear(int month, int year){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		
		return getDistinctJobOrderItemAndDateRange(firstDayOfMonth.toDate(), lastDayOfMonth.toDate());
	}
	
	public List<Item> getDistinctJobOrderItemAndDateRange(Date dateFrom, Date dateTo){
		Date processedDateFrom = CommonUtil.convertDateAsStartDate(dateFrom);
		Date processedDateTo = CommonUtil.convertDateAsEndDate(dateTo);
		return jobItemRepo.findDistinctJobItemDateRange(processedDateFrom, processedDateTo);
	}
	
	public Page<JobOrder> getJobOrderByCustomerIdAndDateRange(String customerId, Date dateFrom, Date dateTo, Pageable pageable){
		return joRepo.findAll(JobOrderSpecifications.byCustomerIdAndDateRange(customerId, dateFrom, dateTo), pageable);
	}
	
	public Long getSumItemUnitByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo){
		return jobItemRepo.sumByItemIdAndPoDateRange(itemId, dateFrom, dateTo);
	}
	
	public List<JobItem> getJobItemByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo){ 
		return jobItemRepo.findByItemIdAndPoDateRange(itemId, dateFrom, dateTo);
	}
	
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
		
		JobOrder savedJob = joRepo.save(jo);

		if(StringUtils.isEmpty(jo.getId())){
			codeTrackService.incrementJobCode();
		}
		
		return savedJob;
	}
	
	public void deleteJob(String id){
		joRepo.delete(id); 
	}
	
	public JobOrder getJobById(String id){
		return joRepo.findOne(id);
	}

}
