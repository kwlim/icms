package com.lkwy.category.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="item_category")
public class ItemCategory extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = 8133014095551879655L;
	
	@NotEmpty
	private String name;
	
	private String remark;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
