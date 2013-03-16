package com.lkwy.purchase.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.lkwy.item.entity.Item;

@StaticMetamodel(StockOrder.class)
public class StockOrder_ {
	
	public static volatile SingularAttribute<StockOrder, Item> item;
	public static volatile SingularAttribute<StockOrder, PurchaseOrder> purchaseOrder;
	
}
