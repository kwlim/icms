package com.lkwy.job.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.item.entity.Item;

@Entity
@Table(name="job_item")
public class JobItem  extends AbstractAuditablePersistable{

	private static final long serialVersionUID = -1590673472272474949L;

	@ManyToOne
	private JobOrder jobOrder;
	
	@OneToOne
	private Item item;
	
	private Float stockPrice;
	private Float markup;
	private Float price;
	private Integer unit;
	private Float labour;
	public JobOrder getJobOrder() {
		return jobOrder;
	}
	public void setJobOrder(JobOrder jobOrder) {
		this.jobOrder = jobOrder;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Float getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(Float stockPrice) {
		this.stockPrice = stockPrice;
	}
	public Float getMarkup() {
		return markup;
	}
	public void setMarkup(Float markup) {
		this.markup = markup;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public Float getLabour() {
		return labour;
	}
	public void setLabour(Float labour) {
		this.labour = labour;
	}
}
