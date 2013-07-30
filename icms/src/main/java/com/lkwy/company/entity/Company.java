package com.lkwy.company.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="company")
public class Company extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = -8311691749851939904L;
	
	@NotNull
	@Index(name="idx_companyKey")
	private String companyKey;
	private String name;
	private String registrationNumber;
	@Lob
	private String address;
	private String phoneNumber;
	private String faxNumber;
	private String logoPath;
	
	public Company() {
		
	}
	
	public Company(String companyKey){
		this.companyKey = companyKey;
	}
	
	public String getCompanyKey() {
		return companyKey;
	}

	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	
}
