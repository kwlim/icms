package com.lkwy.company.view;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lkwy.company.entity.Company;
import com.lkwy.company.service.CompanyService;

@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value="company", method=RequestMethod.GET)
	public String edit(ModelMap model){
		
		Company company = companyService.getCompanyCreateIfNotExist();
		model.addAttribute("company", company);
		
		return "company/profile";
	}
	
	@RequestMapping(value="company", method=RequestMethod.POST)
    public String edit(ModelMap model, RedirectAttributes redirectAttributes, @Valid Company company, BindingResult result){
		
		if(result.hasErrors()){
			model.addAttribute("company", company);
			return "company/profile";
		}
		
		companyService.saveCompany(company);
		redirectAttributes.addFlashAttribute("message", "company.profile.updateSuccess");
		
		return "redirect:/company";
	}

}
