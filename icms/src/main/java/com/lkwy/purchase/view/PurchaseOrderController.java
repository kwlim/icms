package com.lkwy.purchase.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.lkwy.purchase.entity.PurchaseOrder;
import com.lkwy.purchase.service.PurchaseOrderService;
import com.lkwy.vendor.entity.Vendor;
import com.lkwy.vendor.service.VendorService;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	PurchaseOrderService poService;
	
	@ModelAttribute("allVendorList")
	public List<Vendor> getAllVendor(){
		return vendorService.getAllVendor();
	}
	
	protected boolean submittionHasErrors(ModelMap model, PurchaseOrder po, BindingResult result){
		boolean submittionError = false;
		
		submittionError = result.hasErrors();
		
		if(po.getVendor() == null || StringUtils.isEmpty(po.getVendor().getId())){
			submittionError = true;
			result.rejectValue("vendor.id", "NotNull.purchaseOrder.vendor");
		}
		
		return submittionError;
	}
	
	@RequestMapping(value="/save/submit", method=RequestMethod.POST)
	public String submitPo(ModelMap model, RedirectAttributes redirectAttributes, @Valid PurchaseOrder po, BindingResult result){
		
		if(submittionHasErrors(model, po, result)){
			model.addAttribute("purchaseOrder", po);
			return "po/new";
		}
		
		poService.savePo(po);
		if(StringUtils.isEmpty(po.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "po.new.success.message");
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "po.edit.success.message");
		}
		
		return "redirect:/po/";
		
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deletePo(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                poService.deletePo(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "po.delete.success.message");
        }
        return "redirect:/po/";
	}
	
	@RequestMapping("/edit/{id}")
	public String editPo(ModelMap model, @PathVariable("id") String id){
		PurchaseOrder po = poService.getPoById(id);
		model.addAttribute("purchaseOrder", po);
		
		return "po/new";
	}
	
	@RequestMapping("/new")
	public String newPo(ModelMap model){
		
		PurchaseOrder po = new PurchaseOrder();
		model.addAttribute("purchaseOrder", po);
		
		return "po/new";
	}
	
	
	
	@RequestMapping(value={"/", ""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value="poNumber", required=false) String poNumber, 
			@RequestParam(value="poDateFrom", required=false) Date poDateFrom,
			@RequestParam(value="poDateTo", required=false) Date poDateTo,
			@RequestParam(value="vendorId", required=false) String vendorId){
		
		String id = "po";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "poNumber", "vendor.companyName", "modifiedDate"}, "modifiedDate");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("poNumber", poNumber);
        model.addAttribute("poDateFrom", poDateFrom);
        model.addAttribute("poDateTo", poDateTo);
        model.addAttribute("vendorId", vendorId);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<PurchaseOrder> page = poService.getPoByPoNumberDateVendor(poNumber, null, null, vendorId, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "po/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }

}
