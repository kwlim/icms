package com.lkwy.sample.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="sample_entity")
public class SampleEntityObject extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = 6884026736459484136L;
	
	private String name;

	public SampleEntityObject() {
		
	}
	
	public SampleEntityObject(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
