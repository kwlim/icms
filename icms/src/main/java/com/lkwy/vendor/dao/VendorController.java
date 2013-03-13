package com.lkwy.vendor.dao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lkwy.vendor.entity.Vendor;
import com.lkwy.vendor.service.VendorService;

@Controller
@RequestMapping("/vendor")
public class VendorController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);
	
	@Autowired
	VendorService vendorService;
	
	@RequestMapping("/new")
	public String newVendor(ModelMap model){
		
		Vendor vendor = new Vendor();
		model.addAttribute("vendor", vendor);
		
		return "vendor/new";
	}
	
	@RequestMapping("/view/{id}")
	public String viewVendor(ModelMap model, @PathVariable("id") String id){
		
		Vendor vendor = vendorService.getVendorById(id);
		model.addAttribute("vendor", vendor);
		
		return "vendor/view";
	}
	
	@RequestMapping("/edit/{id}")
	public String editVendor(ModelMap model, @PathVariable("id") String id){
		
		Vendor vendor = vendorService.getVendorById(id);
		model.addAttribute("vendor", vendor);
		
		return "vendor/new";
	}
	
	@RequestMapping("/save/submit")
	public String submitVendor(ModelMap model, RedirectAttributes redirectAttributes, @Valid Vendor vendor, BindingResult result){
		
		if(result.hasErrors()){
			model.addAttribute("vendor", vendor);
			return "vendor/new";
		}
		
		vendorService.saveVendor(vendor);
		if(StringUtils.isEmpty(vendor.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "vendor.new.success.message");
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "vendor.edit.success.message");
		}
		
		return "redirect:/vendor/";
	}
	
	@RequestMapping(value={"/",""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value = "name", required = false) String name){
		
		String id = "vendor";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "companyName", "contactPerson", "contactNumber", "modifiedDate"}, "companyName");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<Vendor> page = vendorService.getVendorByCompanyNameContactPersonContactNumber(name, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "vendor/list";
	}
	
	@RequestMapping("delete")
    public String deleteItem(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                vendorService.deleteVendor(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "vendor.delete.success.message");
        }
        return "redirect:/vendor/";
	}

}
