package com.lkwy.billing.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lkwy.billing.dao.IBillingRepository;
import com.lkwy.billing.dao.IPaymentRepository;
import com.lkwy.billing.entity.Billing;
import com.lkwy.billing.entity.Payment;
import com.lkwy.security.service.SecurityManager;

@Service
public class BillingService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(SecurityManager.class);
	
	public static String FIX_BILLING_KEY = "icms_billing";
	
	@Autowired
	private IBillingRepository billingRepo;
	
	@Autowired
	private IPaymentRepository paymentRepo;
	
	public Page<Payment> getPaymentList(Pageable pageable){
		
		return paymentRepo.findByBilling_billingKey(FIX_BILLING_KEY, pageable);
	}
	
	public boolean isExpired(){
		
		Date now = new Date();
		Billing billing = getBillingCreateIfNotExist();
		if(billing.getExpiryDate() == null){
			LOGGER.debug("BillingService|isExpired|billing date is NULL");
			return true;
		}
		else if(billing.getExpiryDate().before(now)){
			LOGGER.debug("BillingService|isExpired|billing date is before now");
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public Billing saveBilling(Billing billing){
		return billingRepo.save(billing);
	}
	
	public Payment savePayment(Payment payment){
		
		payment.setBilling(getBillingCreateIfNotExist());
		
		return paymentRepo.save(payment);
	}
	
	public Billing getBillingCreateIfNotExist(){
		
		Billing billing = billingRepo.findOneByBillingKey(FIX_BILLING_KEY);
		
		if(billing == null){
			billing = billingRepo.save(new Billing(FIX_BILLING_KEY));
		}
		
		return billing;
	}

}
