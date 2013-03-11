package com.lkwy.purchase.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.item.entity.Item;

@Entity
@Table(name="stock_order")
public class StockOrder extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = -2171543983268850506L;

	@ManyToOne
	private PurchaseOrder purchaseOrder;
	
	@OneToOne
	private Item item;
	
	private Integer quantity;
	private Float unitPrice;
	
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}
}
