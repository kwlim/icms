package com.lkwy.stock.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.lkwy.common.util.CommonUtil;
import com.lkwy.item.entity.Item;
import com.lkwy.item.service.ItemService;
import com.lkwy.job.entity.JobItem;
import com.lkwy.job.service.JobOrderService;
import com.lkwy.purchase.entity.StockOrder;
import com.lkwy.purchase.service.PurchaseOrderService;
import com.lkwy.stock.entity.StockBringForward;
import com.lkwy.stock.entity.StockCheck;

@Service
public class StockCheckService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(StockCheckService.class);
	
	@Autowired
	StockBringForwardService stockBfService;
	
	@Autowired
	PurchaseOrderService poService;
	
	@Autowired
	JobOrderService jobService;
	
	@Autowired
	ItemService itemService;
	
	public Long getStockBalanceForItem(String itemId, int month, int year){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		Date lastDayOfMonthEndDate = CommonUtil.convertDateAsEndDate(lastDayOfMonth.toDate());
		
		StockBringForward stockBf = stockBfService.getStockBringFowardCreateIfApplicable(month, year, itemId);
		Long totalPurchase = poService.getSumStockOrderQuantityByItemIdAndPoDateRange(itemId, firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		Long totalSold = jobService.getSumItemUnitByItemIdAndPoDateRange(itemId, firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		
		long balance = 0l;
		
		if(stockBf != null){
			balance += stockBf.getUnit();
		}
		if(totalPurchase != null){
			balance += totalPurchase;
		}
		if(totalSold != null){
			balance -= totalSold;
		}
		
		return balance;
	}
	
	
	public StockBringForward generateStockBringForward(String itemId, int month, int year){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastMonth = firstDayOfMonth.minusDays(1);
		
		long balanceFromLastMonth = getStockBalanceForItem(itemId, lastMonth.getMonthOfYear(), lastMonth.getYear());
		LOGGER.debug("StockCheckService|generateStockBringForward|{}|{} - {}", new Object[]{month, year, balanceFromLastMonth});
		
		if(balanceFromLastMonth > 0){
			
			StockBringForward stockBf = new StockBringForward();
			Item item = itemService.getItemById(itemId);
			stockBf.setItem(item);
			stockBf.setBfDate(firstDayOfMonth.toDate());
			stockBf.setUnit((int)balanceFromLastMonth);
			stockBf.setCreatedDate(firstDayOfMonth.toDate());
			
			return stockBfService.saveStockBringForward(stockBf);
		}
		
		return null;
	}
	
	public List<StockCheck> getStockCheckListForItemOnDateRange(String itemId, Integer monthFrom, Integer yearFrom, Integer monthTo, Integer yearTo){
		
		if(monthFrom == null && yearFrom == null){
			return Lists.newArrayList();
		}
		
		DateTime firstDayOfDateFrom = new DateTime(yearFrom, monthFrom, 1, 0, 0);
		DateTime firstDayOfDateTo = new DateTime(yearFrom, monthFrom, 2, 0, 0);
		
		if(monthTo != null && yearTo != null){
			firstDayOfDateTo = new DateTime(yearTo, monthTo, 2, 0, 0);
		}
		
		List<StockCheck> returnResultList = Lists.newArrayList();
		
		if(firstDayOfDateTo.isBefore(firstDayOfDateFrom)){
			return returnResultList;
		}
		
		DateTime loopDateTime = firstDayOfDateFrom;
		do{
			
			returnResultList.addAll(getStockCheckListForItem(itemId, loopDateTime.getMonthOfYear(), loopDateTime.getYear()));
			
			loopDateTime = loopDateTime.plusMonths(1);
		}while(loopDateTime.isBefore(firstDayOfDateTo));
		
		return returnResultList;
	}
	
	public List<StockCheck> getStockCheckListForItem(String itemId, int month, int year){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastDayOfMonth = firstDayOfMonth.dayOfMonth().withMaximumValue();
		Date lastDayOfMonthEndDate = CommonUtil.convertDateAsEndDate(lastDayOfMonth.toDate());
		SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM/yyyy");
		
		List<StockCheck> stockCheckList = Lists.newArrayList();
		List<StockCheck> transactionStockCheckList = Lists.newArrayList();
		
		StockBringForward stockBf = stockBfService.getStockBringFowardCreateIfApplicable(month, year, itemId);
		//if no bring forward from last month, calculate and create new
		if(stockBf == null){
			stockBf = generateStockBringForward(itemId, month, year);
		}
		
		List<StockOrder> stockOrderList = poService.getStockOrderByItemIdAndPoDateRange(itemId, firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		List<JobItem> jobItemList = jobService.getJobItemByItemIdAndPoDateRange(itemId, firstDayOfMonth.toDate(), lastDayOfMonthEndDate);
		
		if(stockBf != null){
			DateTime lastMonth = firstDayOfMonth.minusMonths(1);
			StringBuilder desc = new StringBuilder("Balance from last month (");
			desc.append(monthYearFormat.format(lastMonth.toDate()));
			desc.append(")");
			stockCheckList.add(new StockCheck(stockBf.getBfDate(), stockBf.getItem(), stockBf.getUnit(), StockCheck.STOCK_CHECK_TYPE.BRING_FORWARD.getValue(), desc.toString()));
		}
		
		if(stockOrderList != null && !stockOrderList.isEmpty()){
			for(StockOrder so: stockOrderList){
				StringBuilder desc = new StringBuilder("Purchase Order (");
				desc.append(so.getPurchaseOrder().getPoNumber());
				desc.append(")");
				transactionStockCheckList
					.add(new StockCheck(so.getPurchaseOrder().getPoDate(), so.getItem(), so.getQuantity(), StockCheck.STOCK_CHECK_TYPE.PURCHASE.getValue(), desc.toString(), so.getPurchaseOrder().getId(), (double)so.getUnitPrice().floatValue()));
			}
		}
		
		if(jobItemList != null && !jobItemList.isEmpty()){
			for(JobItem jo: jobItemList){
				StringBuilder desc = new StringBuilder("Job Order (");
				desc.append(jo.getJobOrder().getJobNumber());
				desc.append(")");
				transactionStockCheckList.add(new StockCheck(jo.getJobOrder().getJobDate(), jo.getItem(), jo.getUnit(), StockCheck.STOCK_CHECK_TYPE.JOB.getValue(), desc.toString(), jo.getJobOrder().getId(), (double)jo.getStockPrice().floatValue()));
			}
		}
		
		Collections.sort(transactionStockCheckList, new StockCheckDateComparable());
		
		stockCheckList.addAll(transactionStockCheckList);
		
		return stockCheckList;
	}
	
	public class StockCheckDateComparable implements Comparator<StockCheck>{
		
	    @Override
	    public int compare(StockCheck o1, StockCheck o2) {
	        return (o1.getDate().before(o2.getDate()) ? 0 : 1);
	    }
	}
	
}
