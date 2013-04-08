package com.lkwy.customer.dao;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.lkwy.common.entity.AutoCompleteDTO;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.customer.entity.Customer;
import com.lkwy.customer.service.CustomerService;
import com.lkwy.job.entity.JobOrder;
import com.lkwy.job.service.JobOrderService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	JobOrderService jobService;
	
	@RequestMapping("/json/list")
	public @ResponseBody List<AutoCompleteDTO> jsonGetCustomer(String carPlateNumber){
		
		List<AutoCompleteDTO> acList = Lists.newArrayList();
		List<Customer> customerList =customerService.getCustomerByLikeCarPlateNumber(carPlateNumber); 
		for(Customer customer: customerList){
			acList.add(new AutoCompleteDTO(customer.getId(), customer.getCarPlateNumber()));
		}
		
		LOGGER.debug("jsonGetCustomer|{}|", carPlateNumber, acList.size());
		
		return acList;
	}
	
	@RequestMapping("delete")
	public String deleteCustomer(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                customerService.deleteCustomer(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "customer.delete.success.message");
        }
        return "redirect:/customer/";
	}
	
	@RequestMapping(value="/save/submit", method=RequestMethod.POST)
	public String submitCustomer(ModelMap model, RedirectAttributes redirectAttributes, @Valid Customer customer, BindingResult result){
		
		if(result.hasErrors()){
			model.addAttribute("customer", customer);
			return "customer/new";
		}
		
		customerService.saveCustomer(customer);
		if(StringUtils.isEmpty(customer.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "customer.new.success.message");
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "customer.edit.success.message");
		}
		
		return "redirect:/customer/";
		
	}
	
	@RequestMapping("/edit/{id}")
	public String editCustomer(ModelMap model, @PathVariable("id") String id){

		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		
		return "customer/new";
	}
	
	@RequestMapping("/view/{id}")
	public String viewCustomer(ModelMap model, HttpServletRequest request,
			@PathVariable("id") String id,
			@RequestParam(value="dateFrom", required=false) Date dateFrom,
			@RequestParam(value="dateTo", required=false) Date dateTo){
		
		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		
		String tableId = "job";
        Integer start = DisplayTagUtil.getListStart(id, request, null);
		Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.DESC, "jobDate");
        
        Page<JobOrder> page = jobService.getJobOrderByCustomerIdAndDateRange(id, dateFrom, dateTo, pageable);
		
        model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		model.addAttribute("id", tableId);
		
		return "customer/view";
	}
	
	@RequestMapping("/new")
	public String newCustomer(ModelMap model){
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
		return "customer/new";
	}
	
	@RequestMapping(value={"/",""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value = "name", required = false) String name){
		
		String id = "customer";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "carPlateNumber", "name", "contactNumber", "modifiedDate"}, "carPlateNumber");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<Customer> page = customerService.getCustomerByCarPlateNameContact(name, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "customer/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
}
