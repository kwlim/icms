package com.lkwy.job.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.customer.entity.Customer;
import com.lkwy.customer.service.CustomerService;
import com.lkwy.job.entity.JobOrder;
import com.lkwy.job.service.JobOrderService;

@Controller
@RequestMapping("/jo")
public class JobOrderController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(JobOrderController.class);
	
	@Autowired
	JobOrderService jobService;
	
	@Autowired
	CustomerService customerService;
	
	@ModelAttribute("allCustomerList")
	public List<Customer> getAllCustomer(){
		return customerService.getAllCustomer();
	}
	
	@RequestMapping(value="/save/submit", method=RequestMethod.POST)
	public String submitPo(ModelMap model, RedirectAttributes redirectAttributes, @Valid JobOrder jo, BindingResult result){
		
		if(submittionHasErrors(model, jo, result)){
			model.addAttribute("jobOrder", jo);
			
			return "jo/new";
		}
		
		JobOrder savedJo = jobService.saveJo(jo);
		if(StringUtils.isEmpty(jo.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "jo.new.success.message");
			return "redirect:/jo/edit/"+savedJo.getId();
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "jo.edit.success.message");
		}
		
		return "redirect:/jo/";
		
	}
	
	protected boolean submittionHasErrors(ModelMap model, JobOrder job, BindingResult result){
		boolean submittionError = false;
		
		submittionError = result.hasErrors();
		
		if(job.getCustomer() == null || StringUtils.isEmpty(job.getCustomer().getId())){
			submittionError = true;
			result.rejectValue("customer.id", "NotNull.jo.customer");
		}
		
		return submittionError;
	}
	
	@RequestMapping("/edit/{id}")
	public String editPo(ModelMap model, @PathVariable("id") String id){
		JobOrder job = jobService.getJoById(id);
		model.addAttribute("jobOrder", job);
		
		return "jo/new";
	}
	
	@RequestMapping("/new")
	public String newJobOrder(ModelMap model){
		
		JobOrder job = new JobOrder();
		model.addAttribute("jobOrder", job);
		
		return "jo/new";
	}
	
	@RequestMapping(value={"/", ""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value="joNumberCustomer", required=false) String joNumberCustomer, 
			@RequestParam(value="joDateFrom", required=false) Date joDateFrom,
			@RequestParam(value="joDateTo", required=false) Date joDateTo){
		
		String id = "jo";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "joNumber", "modifiedDate"}, "modifiedDate");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("joNumberCustomer", joNumberCustomer);
        model.addAttribute("joDateFrom", joDateFrom);
        model.addAttribute("joDateTo", joDateTo);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<JobOrder> page = jobService.getJobOrderByJobNumberCarPlateJobDateRange(joNumberCustomer, joDateFrom, joDateTo, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "jo/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }

}
