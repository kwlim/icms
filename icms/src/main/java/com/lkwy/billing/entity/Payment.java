package com.lkwy.billing.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="payment")
public class Payment extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = -8293297799908520581L;
	
	@NotNull
	private Date paymentDate;
	private Float amount;
	
	@ManyToOne
	private Billing billing;

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	
}
