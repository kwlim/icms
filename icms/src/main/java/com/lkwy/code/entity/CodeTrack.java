package com.lkwy.code.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lkwy.common.entity.AbstractPersistable;

@Entity
@Table(name="code_track")
public class CodeTrack extends AbstractPersistable{
	
	private static final long serialVersionUID = 6742775167358220313L;
	
	private String moduleKey;
	private Integer counter;
	
	public CodeTrack() {
		
	}
	
	public CodeTrack(String moduleKey, int counter) {
		this.moduleKey = moduleKey;
		this.counter = counter;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
}
