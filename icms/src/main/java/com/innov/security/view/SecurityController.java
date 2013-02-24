package com.innov.security.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.innov.common.user.util.UserUtil;

@Controller
public class SecurityController {
    
    @RequestMapping("/login")
    public String login(){
    	if(UserUtil.isAnnonymous()){
    		return "login";
    	}
    	else{
    		return "redirect:/home";
    	}
    }

}
