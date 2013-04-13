package com.lkwy.home;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.lkwy.job.service.JobOrderService;

@Controller
public class HomeController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private JobOrderService jobService;
    
    @RequestMapping(value={"/","/dashboard"})
    public String home(ModelMap model){
        
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMM(EE)");
    	
        DateTime now = new DateTime();
        DateTime tempDate = now.minusDays(9);
        
        LinkedHashMap<String, Double> sumPriceMap = Maps.newLinkedHashMap();
        LinkedHashMap<String, Double> sumEarningMap = Maps.newLinkedHashMap();
        
        do{
        	Double price = jobService.getSumJobPrice(tempDate.toDate());
        	Double earning = jobService.getSumMarkupLabourPrice(tempDate.toDate());
        	
        	if(price == null){
        		price = 0d;
        	}
        	if(earning == null){
        		earning = 0d;
        	}
        	
        	sumPriceMap.put(sdf.format(tempDate.toDate()), price);
        	sumEarningMap.put(sdf.format(tempDate.toDate()), earning);
        	
        	tempDate = tempDate.plusDays(1);
        	
        }while(tempDate.isBefore(now) || tempDate.isEqual(now));
        
        model.put("priceMap", sumPriceMap);
        model.put("earningMap", sumEarningMap);
        
        return "home";
    }
    
    @RequestMapping("/unauthorized")
    public String unauthorized()    {
        return "unauthorized";
    }
    
    
}