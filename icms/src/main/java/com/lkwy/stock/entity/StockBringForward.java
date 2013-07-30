package com.lkwy.stock.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.item.entity.Item;

@Entity
@Table(name="stock_bf")
public class StockBringForward extends AbstractAuditablePersistable{

	private static final long serialVersionUID = -7566447990882341583L;

	@Temporal(TemporalType.DATE)
	@Index(name="idx_bfDate")
	private Date bfDate;
	
	@OneToOne
	private Item item;
	
	private Integer unit;
	
	public Date getBfDate() {
		return bfDate;
	}

	public void setBfDate(Date bfDate) {
		this.bfDate = bfDate;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	
}
