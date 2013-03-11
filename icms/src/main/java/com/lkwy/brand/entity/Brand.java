package com.lkwy.brand.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="brand")
public class Brand extends AbstractAuditablePersistable{

	private static final long serialVersionUID = 5743662836934298441L;
	
	@NotEmpty
	@Column(unique=true, nullable=false)
	private String name;
	
	public Brand() {
		
	}
	
	public Brand(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
