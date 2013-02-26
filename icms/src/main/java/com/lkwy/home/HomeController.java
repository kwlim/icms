package com.lkwy.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    
    @RequestMapping(value={"/","/home"})
    public String home(ModelMap model){
        
        LOGGER.debug("HOME CONTROLLER~!");
        
        return "home";
    }
    
    @RequestMapping("/unauthorized")
    public String unauthorized()    {
        return "unauthorized";
    }
    
    
}