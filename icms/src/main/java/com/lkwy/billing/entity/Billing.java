package com.lkwy.billing.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="billing")
public class Billing extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = -8997482244888252238L;
	
	@NotNull
	private String billingKey;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date expiryDate;

	@OneToMany(mappedBy="billing")
	private List<Payment> paymentList;
	
	public Billing() {
		
	}
	
	public Billing(String billingKey) {
		this.billingKey = billingKey;
	}
	
	public String getBillingKey() {
		return billingKey;
	}

	public void setBillingKey(String billingKey) {
		this.billingKey = billingKey;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}
	
}
