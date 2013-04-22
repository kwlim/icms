package com.lkwy.billing.service;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	public Payment getPaymentById(String paymentId){
		return paymentRepo.findOne(paymentId);
	}
	
	public void deletePayment(String paymentId){
		paymentRepo.delete(paymentId);
	}
	
	public Page<Payment> getPaymentList(Pageable pageable){
		
		return paymentRepo.findByBilling_billingKey(FIX_BILLING_KEY, pageable);
	}
	
	public Payment findLatestPaymentDate(){
		
		Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "paymentDate");
		Page<Payment> paymentList = paymentRepo.findAll(pageable);
		
		if(paymentList.getSize() > 0){
			return paymentList.iterator().next();
		}
		else{
			return null;
		}
	}
	
	public boolean isExpired(){
		
		Billing billing = getBillingCreateIfNotExist();
		
		if(billing.getExpiryDate() == null){
			LOGGER.debug("BillingService|isExpired|billing date is NULL");
			return true;
		}
		else {
			DateTime expiryDate = new DateTime(billing.getExpiryDate());
			LocalDate expiryLocalDate = expiryDate.toLocalDate();
			LocalDate now = new DateTime().toLocalDate();
			return expiryLocalDate.isBefore(now) && !expiryLocalDate.isEqual(now);
		}
	}
	
	public Billing saveBilling(Billing billing){
		return billingRepo.save(billing);
	}
	
	public Payment savePaymentAndCalculateExpiry(Payment payment){
		
		Billing billing = getBillingCreateIfNotExist();
		payment.setBilling(getBillingCreateIfNotExist());
		
		Payment savedPayment = paymentRepo.save(payment);
		
		Payment latestPayment = findLatestPaymentDate();
		DateTime expiryDate = new DateTime();
		
		if(latestPayment != null){
			expiryDate = new DateTime(latestPayment.getPaymentDate());
			expiryDate = expiryDate.plusDays(30);
		}else{
			expiryDate = expiryDate.minusDays(1);
		}
		 
		billing.setExpiryDate(expiryDate.toDate());
		saveBilling(billing);
		
		return savedPayment;
	}
	
	public Billing getBillingCreateIfNotExist(){
		
		Billing billing = billingRepo.findOneByBillingKey(FIX_BILLING_KEY);
		
		if(billing == null){
			billing = billingRepo.save(new Billing(FIX_BILLING_KEY));
		}
		
		return billing;
	}

}
