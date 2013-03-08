package com.lkwy.customer.dao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.customer.entity.Customer;
import com.lkwy.customer.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("delete")
	public String deleteCustomer(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                customerService.deleteCustomer(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "customer.delete.success.message");
        }
        return "redirect:/customer/list";
	}
	
	@RequestMapping("/save/submit")
	public String submitCustomer(ModelMap model, RedirectAttributes redirectAttributes, @Valid Customer customer, BindingResult result){
		
		if(result.hasErrors()){
			model.addAttribute("customer", customer);
			return "customer/new";
		}
		
		customerService.saveCustomer(customer);
		if(StringUtils.isEmpty(customer.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "customer.new.success.message");
			return "redirect:/customer/list";
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "customer.edit.success.message");
			return "redirect:/customer/view/" + customer.getId();
		}
		
		
		
	}
	
	@RequestMapping("/edit/{id}")
	public String editCustomer(ModelMap model, @PathVariable("id") String id){

		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		
		return "customer/new";
	}
	
	@RequestMapping("/view/{id}")
	public String viewCustomer(ModelMap model, @PathVariable("id") String id){

		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		
		return "customer/view";
	}
	
	@RequestMapping("/new")
	public String newCustomer(ModelMap model){
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
		return "customer/new";
	}
	
	@RequestMapping("/list")
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

}
