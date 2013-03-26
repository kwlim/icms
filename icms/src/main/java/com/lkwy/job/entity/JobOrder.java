package com.lkwy.job.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.customer.entity.Customer;

@Entity
@Table(name="job_order")
public class JobOrder extends AbstractAuditablePersistable{

	private static final long serialVersionUID = 4711879357247741629L;
	
	@NotEmpty
	private String jobNumber;
	
	@Lob
	private String remark;
	
	private Date jobDate;
	
	@OneToMany(mappedBy="jobOrder", cascade=CascadeType.ALL)
	private List<JobItem> jobItemList;
	
	@OneToOne
	private Customer customer;

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
}
