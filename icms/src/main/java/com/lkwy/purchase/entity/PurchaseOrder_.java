package com.lkwy.purchase.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.lkwy.vendor.entity.Vendor;

@StaticMetamodel(PurchaseOrder.class)
public class PurchaseOrder_ {
	
	public static volatile SingularAttribute<PurchaseOrder, String> poNumber;
	public static volatile SingularAttribute<PurchaseOrder, Date> poDate;
	public static volatile SingularAttribute<PurchaseOrder, Vendor> vendor;
	public static volatile ListAttribute<PurchaseOrder, StockOrder> stockOrderList;
	
}
