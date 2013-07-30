package com.lkwy.purchase.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.vendor.entity.Vendor;

@Entity
@Table(name="purchase_order")
public class PurchaseOrder extends AbstractAuditablePersistable{
	
	private static final long serialVersionUID = -4064344215763318559L;
	@NotEmpty
	@Index(name="idx_poNumber")
	private String poNumber;
	
	@NotNull
	@Index(name="idx_poDate")
	private Date poDate;
	private Double price;
	
	@Lob
	private String remark;
	
	@OneToOne
	private Vendor vendor;
	
	@OneToMany(mappedBy="purchaseOrder", cascade=CascadeType.ALL)
	private List<StockOrder> stockOrderList;
	
	public String getPoNumber() {
		return poNumber;
	}
	
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<StockOrder> getStockOrderList() {
		return stockOrderList;
	}

	public void setStockOrderList(List<StockOrder> stockOrderList) {
		this.stockOrderList = stockOrderList;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
