package com.lkwy.security.view;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lkwy.common.user.util.UserUtil;
import com.lkwy.user.entity.UserProfileDTO;

@Controller
public class SecurityController {
    
	static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
	
    @RequestMapping("/login")
    public String login(){
    	if(UserUtil.isAnnonymous()){
    		return "login";
    	}
    	else{
    		return "redirect:/home";
    	}
    }
    
    @RequestMapping("/forgotPassword")
    public String forgotPassword(ModelMap model){
    	LOGGER.debug("forgotPassword");
    	UserProfileDTO userForm = new UserProfileDTO();
    	model.put("userForm", userForm);
    	
    	return "forgotPassword";
    }
    
    @RequestMapping(value="/forgotPassword", method=RequestMethod.POST)
    public String submitForgotPassword(ModelMap model, UserProfileDTO userForm, BindingResult result){
    	LOGGER.debug("submitForgotPassword|{}|{}", userForm.getUsername(), userForm.getEmail());
    	
    	if(invalidForgotPasswordForm(userForm, result)){
    		model.put("userForm", userForm);
        	return "forgotPassword";
    	}
    	
    	return "redirect:/login";
    }
    
    public boolean invalidForgotPasswordForm(UserProfileDTO userForm, BindingResult result){
    	boolean invalid = false;
    	
    	if(StringUtils.isEmpty(userForm.getUsername())){
    		invalid = true;
    		result.rejectValue("username", "NotEmpty.userProfileDTO.username");
    	}
    	if(StringUtils.isEmpty(userForm.getEmail())){
    		invalid = true;
    		result.rejectValue("email", "NotEmpty.userProfileDTO.email");
    	}
    	
    	return invalid;
    }
    
}
