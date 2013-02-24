package com.innov.common.user.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    
	public static String ANNONYMOUS = "annonymous";
	
    public static String getLoginUsername(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            return auth.getName();
        }else{
            return ANNONYMOUS;
        }
    }
    
    public static boolean isAnnonymous(){
    	return StringUtils.equals(ANNONYMOUS, getLoginUsername());
    }
    
}
