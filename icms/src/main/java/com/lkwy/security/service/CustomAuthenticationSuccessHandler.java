package com.lkwy.security.service;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.lkwy.billing.entity.Billing;
import com.lkwy.billing.service.BillingService;
import com.lkwy.common.user.util.UserUtil;
import com.lkwy.user.entity.User;
import com.lkwy.user.service.UserService;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	
	@Autowired
	private BillingService billingService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		
		Billing billing = billingService.getBillingCreateIfNotExist();
		
		String loginUsername = UserUtil.getLoginUsername();
		User loginUser = userService.getUserByUsername(loginUsername);
		
		if(billing.getExpiryDate() != null && !loginUser.getIsAdmin()){
			DateTime expiredDue = new DateTime(billing.getExpiryDate());
			DateTime warningExpiredDate = expiredDue.minusDays(7);
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
			if(warningExpiredDate.isBeforeNow()){
				StringBuilder msg = new StringBuilder("Your account going to expired on ");
				msg.append(sdf.format(expiredDue.toDate())).append(". Please make payment before account is locked");
				
				request.getSession().setAttribute("generalmessage", msg.toString());
			}
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
