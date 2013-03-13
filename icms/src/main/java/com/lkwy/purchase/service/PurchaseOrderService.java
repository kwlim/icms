package com.lkwy.purchase.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.purchase.dao.IPurchaseOrderRepository;
import com.lkwy.purchase.dao.IStockOrderRepository;
import com.lkwy.purchase.dao.PurchaseOrderSpecifications;
import com.lkwy.purchase.entity.PurchaseOrder;

@Service
public class PurchaseOrderService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderService.class);
	
	@Autowired
	IPurchaseOrderRepository poRepo;
	
	@Autowired
	IStockOrderRepository stockOrderRepo;
	
	public Page<PurchaseOrder> getPoByPoNumberDateVendor(String poNumber, Date dateFrom, Date dateTo, String vendorId, Pageable pageable){
		String wildCardPoNumber = CommonUtil.addWildCard(poNumber);
		Date processedDateFrom = CommonUtil.convertDateAsStartDate(dateFrom);
		Date processedDateTo = CommonUtil.convertDateAsEndDate(dateTo);
		
		return poRepo.findAll(PurchaseOrderSpecifications.byLikePoNumberPoDateVendorId(wildCardPoNumber, processedDateFrom, processedDateTo, vendorId), pageable);
	}

}
