package com.lkwy.report.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.job.entity.JobPriceCategory;
import com.lkwy.job.service.JobOrderService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private JobOrderService jobService;
	
	@RequestMapping("/priceByCategory")
	public String priceByCategoryReport(ModelMap model,
			@RequestParam(value="jobDateFrom", required=false) Date jobDateFrom,
			@RequestParam(value="jobDateTo", required=false) Date jobDateTo){
		
		List<JobPriceCategory> list = Lists.newArrayList();
		
		if(jobDateFrom == null && jobDateTo == null){
			DateTime today = new DateTime();
			DateTime firstDayOfMonth = today.withDayOfMonth(1);
			jobDateFrom = firstDayOfMonth.toDate();
			DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
			jobDateTo = lastDayOfMonth.toDate();
		}
		
		if(jobDateFrom != null && jobDateTo != null){
			list = jobService.getSumPriceMarkupLabourByItemCategory(jobDateFrom, jobDateTo);
		}
		
		model.put("jobDateFrom", jobDateFrom);
		model.put("jobDateTo", jobDateTo);
		model.put("result", list);
		
		return "/report/priceByCategory";
	}
	
	public void insertDataToMap(List<JobPriceCategory> priceCategoryList, LinkedHashMap<ItemCategory, Double> map, Date date){
		
		for(JobPriceCategory priceCat: priceCategoryList){
			priceCat.setDate(date);
			
			Double price = map.get(priceCat.getCategory());
			if(price == null){
				price = priceCat.getPrice();
			}else{
				price += priceCat.getPrice();
			}
			map.put(priceCat.getCategory(), price);
		}
		
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
	
}
