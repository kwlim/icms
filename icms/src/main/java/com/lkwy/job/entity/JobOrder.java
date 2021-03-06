package com.lkwy.job.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.customer.entity.Customer;

@Entity
@Table(name="job_order")
public class JobOrder extends AbstractAuditablePersistable{

	private static final long serialVersionUID = 4711879357247741629L;
	
	@NotEmpty
	@Index(name="idx_jobNumber")
	private String jobNumber;
	
	@Lob
	private String remark;
	
	@NotNull
	@Index(name="idx_jobDate")
	private Date jobDate;
	
	@OneToMany(mappedBy="jobOrder", cascade=CascadeType.ALL)
	private List<JobItem> jobItemList;
	
	@OneToOne
	private Customer customer;
	
	private Double price;
	
	private Float discount;

	private Integer millage;
	
	@Transient
	private String autoCompleteCarPlateNumber;
	
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	public List<JobItem> getJobItemList() {
		return jobItemList;
	}

	public void setJobItemList(List<JobItem> jobItemList) {
		this.jobItemList = jobItemList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	
	public String getAutoCompleteCarPlateNumber() {
		return autoCompleteCarPlateNumber;
	}
	
	public void setAutoCompleteCarPlateNumber(String autoCompleteCarPlateNumber) {
		this.autoCompleteCarPlateNumber = autoCompleteCarPlateNumber;
	}

	public Integer getMillage() {
		return millage;
	}

	public void setMillage(Integer millage) {
		this.millage = millage;
	}
	
}
