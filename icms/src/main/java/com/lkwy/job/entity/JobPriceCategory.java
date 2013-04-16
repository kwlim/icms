package com.lkwy.job.entity;

import java.util.Date;

import com.lkwy.category.entity.ItemCategory;

public class JobPriceCategory {
	
	private ItemCategory category;
	private Double price;
	private Double earning;
	
	private Date date;
	
	public JobPriceCategory() {
		
	}
	
	public JobPriceCategory(ItemCategory category, Double price) {
		this.category = category;
		this.price = price;
	}

	public JobPriceCategory(ItemCategory category, Double price, Double earning) {
		this.category = category;
		this.price = price;
		this.earning = earning;
	}
	
	public ItemCategory getCategory() {
		return category;
	}

	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getEarning() {
		return earning;
	}

	public void setEarning(Double earning) {
		this.earning = earning;
	}
	
}
