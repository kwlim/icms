package com.lkwy.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkwy.purchase.dao.IPurchaseOrderRepository;
import com.lkwy.purchase.dao.IStockOrderRepository;

@Service
public class PurchaseOrderService {
	
	@Autowired
	IPurchaseOrderRepository poRepo;
	
	@Autowired
	IStockOrderRepository stockOrderRepo;
	
	

}
