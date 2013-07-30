package com.lkwy.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.brand.entity.Brand;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="item")
public class Item extends AbstractAuditablePersistable{

	private static final long serialVersionUID = -7236660524948824135L;
	
	@NotEmpty
	@Column(unique=true, nullable=false)
	@Index(name="idx_code")
	private String code;
	
	@NotEmpty
	@Index(name="idx_name")
	private String name;
	
	@Lob 
	private String remark;
	@OneToOne 
	private ItemCategory category;
	@OneToOne
	private Brand brand;
	
	private Integer lowAmountNotif;
	
	@Transient
	private String newCategoryName;
	
	@Transient
	private String newBrandName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ItemCategory getCategory() {
		return category;
	}
	public void setCategory(ItemCategory category) {
		this.category = category;
	}
	public Integer getLowAmountNotif() {
		return lowAmountNotif;
	}
	public void setLowAmountNotif(Integer lowAmountNotif) {
		this.lowAmountNotif = lowAmountNotif;
	}
	public String getNewCategoryName() {
		return newCategoryName;
	}
	public void setNewCategoryName(String newCategoryName) {
		this.newCategoryName = newCategoryName;
	}
	public String getNewBrandName() {
		return newBrandName;
	}
	public void setNewBrandName(String newBrandName) {
		this.newBrandName = newBrandName;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
}
