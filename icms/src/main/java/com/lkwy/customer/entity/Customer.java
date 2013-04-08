package com.lkwy.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="customer")
public class Customer extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = -561843256200187563L;
	
	@Column(unique=true, nullable=false)
	@NotEmpty
	private String carPlateNumber;
	private String name;
	private String contactNumber;
	private String address;
	
	@Lob
	private String remark;
	
	public Customer() {
		
	}
	
	public Customer(String carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}
	
	public String getCarPlateNumber() {
		return carPlateNumber;
	}
	public void setCarPlateNumber(String carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
