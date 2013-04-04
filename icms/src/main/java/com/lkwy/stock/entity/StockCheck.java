package com.lkwy.stock.entity;

import java.io.Serializable;
import java.util.Date;

import com.lkwy.item.entity.Item;

public class StockCheck implements Serializable{
	
	public static enum STOCK_CHECK_TYPE{
		BRING_FORWARD(0),
		PURCHASE(1),
		JOB(2)
		;
		
		private int value;
		
		private STOCK_CHECK_TYPE(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private static final long serialVersionUID = -583946663364785339L;
	
	private Date date;
	private Item item;
	private Integer unit;
	private int type;
	
	private String description;
	private String parentId;
	private Double price;
	
	public StockCheck() {
		
	}
	
	//for bring forward
	public StockCheck(Date date, Item item, Integer unit, int type, String description, String parentId) {
		this.date = date;
		this.item = item;
		this.unit = unit;
		this.type = type;
		this.description = description;
		this.parentId = parentId;
	}
	
	//for job order, purchase order
	public StockCheck(Date date, Item item, Integer unit, int type, String description, String parentId, Double price) {
		this.date = date;
		this.item = item;
		this.unit = unit;
		this.type = type;
		this.description = description;
		this.parentId = parentId;
		this.price = price;
	}
	
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
