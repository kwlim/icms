package com.lkwy.purchase.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.item.entity.Item;
import com.lkwy.purchase.dao.IPurchaseOrderRepository;
import com.lkwy.purchase.dao.IStockOrderRepository;
import com.lkwy.purchase.dao.PurchaseOrderSpecifications;
import com.lkwy.purchase.entity.PurchaseOrder;
import com.lkwy.purchase.entity.StockOrder;

@Service
public class PurchaseOrderService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderService.class);
	
	@Autowired
	IPurchaseOrderRepository poRepo;
	
	@Autowired
	IStockOrderRepository stockOrderRepo;
	
	public List<Item> getDistinctStockOrderItemAndMonthYear(int month, int year){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		
		return getDistinctStockOrderItemAndDateRange(firstDayOfMonth.toDate(), lastDayOfMonth.toDate());
	}
	
	public List<Item> getDistinctStockOrderItemAndDateRange(Date dateFrom, Date dateTo){
		Date processedDateFrom = CommonUtil.convertDateAsStartDate(dateFrom);
		Date processedDateTo = CommonUtil.convertDateAsEndDate(dateTo);
		return stockOrderRepo.findDistinctStockOrderItemDateRange(processedDateFrom, processedDateTo);
	}
	
	public Page<PurchaseOrder> getPoByVendorIdAndDateRange(String vendorId, Date dateFrom, Date dateTo, Pageable pageable){
		Date processedDateFrom = CommonUtil.convertDateAsStartDate(dateFrom);
		Date processedDateTo = CommonUtil.convertDateAsEndDate(dateTo);
		return poRepo.findAll(PurchaseOrderSpecifications.byVendorIdAndDateRange(vendorId, processedDateFrom, processedDateTo), pageable);
	}
	
	public Long getSumStockOrderQuantityByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo){
		return stockOrderRepo.sumByItemIdAndPoDateRange(itemId, dateFrom, dateTo);
	}
	
	public List<StockOrder> getStockOrderByItemIdAndPoDateRange(String itemId, Date dateFrom, Date dateTo){
		return stockOrderRepo.findByItemIdAndPoDateRange(itemId, dateFrom, dateTo);
	}
	
	public StockOrder getLatestStockItem(String itemId){
		Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "purchaseOrder.poDate");
		List<StockOrder> stockOrderList = stockOrderRepo.findByItem_Id(itemId, pageable);
		if(stockOrderList != null && !stockOrderList.isEmpty()){
			return stockOrderList.iterator().next();
		}
		return null;
	}
	
	public void deleteStockOrder(String soId){
		stockOrderRepo.delete(soId);
	}
	
	public StockOrder saveStockOrder(StockOrder so){
		return stockOrderRepo.save(so);
	}
	
	public PurchaseOrder savePo(PurchaseOrder po){
		return poRepo.save(po);
	}
	
	public void deletePo(String id){
		poRepo.delete(id); 
	}
	
	public PurchaseOrder getPoById(String id){
		return poRepo.findOne(id);
	}
	
	public Page<PurchaseOrder> getPoByPoNumberDateVendor(String poNumber, Date dateFrom, Date dateTo, String vendorId, Pageable pageable){
		String wildCardPoNumber = CommonUtil.addWildCard(poNumber);
		Date processedDateFrom = CommonUtil.convertDateAsStartDate(dateFrom);
		Date processedDateTo = CommonUtil.convertDateAsEndDate(dateTo);
		
		return poRepo.findAll(PurchaseOrderSpecifications.byLikePoNumberPoDateVendorId(wildCardPoNumber, processedDateFrom, processedDateTo, vendorId), pageable);
	}

}
