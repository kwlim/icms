package com.lkwy.billing.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lkwy.billing.entity.Billing;
import com.lkwy.billing.entity.Payment;
import com.lkwy.billing.service.BillingService;
import com.lkwy.common.util.DisplayTagUtil;

@Controller
@RequestMapping("/billing")
public class BillingController {
	
	@Autowired
	private BillingService billingService;
	
	@RequestMapping("/payment/edit/{paymentId}")
	public String editPayment(ModelMap model, HttpServletRequest request, @PathVariable("paymentId") String paymentId){
		
		Billing billing = billingService.getBillingCreateIfNotExist();
		model.addAttribute("billing", billing);
		
		Payment payment = billingService.getPaymentById(paymentId);
		model.addAttribute("payment", payment);
		
		initPaymentList(model, request);
		
		return "/billing/new";
	}
	
	@RequestMapping(value="payment/delete", method=RequestMethod.POST)
    public String deletePayment(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
        if(ids !=  null && ids.length > 0){
            for(String id: ids){
            	billingService.deletePayment(id);
            }
            redirectAttributes.addFlashAttribute("message", "payment.delete.success.message");
        }
        return "redirect:/billing/";
    }
	
	@RequestMapping(value="/payment/save/submit",method=RequestMethod.POST)
	public String submitPayment(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes, @Valid Payment payment, BindingResult result){
		
		Billing billing = billingService.getBillingCreateIfNotExist();
		
		if(result.hasErrors()){
			model.addAttribute("billing", billing);
			
			payment.setBilling(billing);
			model.addAttribute("payment", payment);
			
			initPaymentList(model, request);
			return "/billing/new";
		}
		
		billingService.savePaymentAndCalculateExpiry(payment);
		redirectAttributes.addFlashAttribute("message", "payment.addSuccess");
		
		return "redirect:/billing/";
	}
	
	@RequestMapping(value="/save/submit",method=RequestMethod.POST)
	public String submitBilling(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes, @Valid Billing billing, BindingResult result){
		
		if(invalidBilling(billing, result)){
			model.addAttribute("billing", billing);
			
			Payment payment = new Payment();
			payment.setBilling(billing);
			model.addAttribute("payment", payment);
			
			initPaymentList(model, request);
			return "/billing/new";
		}
		
		billingService.saveBilling(billing);
		redirectAttributes.addFlashAttribute("message", "billing.updateSuccess");
		
		return "redirect:/billing/";
	}
	

	protected boolean invalidBilling(Billing billing, BindingResult result){
		boolean invalid = false;
		
		if(result.hasErrors()){
			invalid = true;
		}
		
		if(billing.getExpiryDate() == null){
			result.rejectValue("expiryDate", "NotNull.billing.expiryDate");
			invalid = true;
		}
		
		return invalid;
	}
	
	
	@RequestMapping("/")
	public String editBilling(ModelMap model, HttpServletRequest request){
		
		Billing billing = billingService.getBillingCreateIfNotExist();
		model.addAttribute("billing", billing);
		
		Payment payment = new Payment();
		model.addAttribute("payment", payment);
		
		initPaymentList(model, request);
		
		return "/billing/new";
	}
	
	
	protected void initPaymentList(ModelMap model, HttpServletRequest request){
		String id = "paymentList";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "paymentDate", "amount", "modifiedDate"}, "paymentDate");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.DESC, sort);
        
        Page<Payment> returnPage = billingService.getPaymentList(pageable); 
        
        model.put("rows", returnPage);
        model.put("size", (int)returnPage.getTotalElements());
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }

}
