package com.lkwy.stock.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
	
	@Scheduled(cron="0 3 * * * *")
	public void calculateStockCheckBfFromLastMonth(){
		LOGGER.debug("StockCheckService|CalculateStockCheckBfFromLastMonth|ScheduleJobRun");
		
		DateTime now = new DateTime();
		recalculateStockBringForwardForAllItem(now.getMonthOfYear(), now.getYear());
	}
	
	public void recalculateStockBringForwardForAllItem(int month, int year){
		LOGGER.debug("RecalculateStockBr|{}|{}", month, year);
		DateTime now = new DateTime(year, month, 1, 0, 0);
		DateTime lastMonth = now.minusMonths(1);
		
		List<Item> stockBfItemList = stockBfService.getDistinctStockBringForwardItemAndMonthYear(lastMonth.getMonthOfYear(), lastMonth.getYear());
		List<Item> purchaseItemList = poService.getDistinctStockOrderItemAndMonthYear(lastMonth.getMonthOfYear(), lastMonth.getYear());
		List<Item> jobItemList = jobService.getDistinctJobOrderItemAndMonthYear(lastMonth.getMonthOfYear(), lastMonth.getYear());
		
		Map<String, Item> distinctItemMap = Maps.newHashMap();
		mergeItem(distinctItemMap, stockBfItemList);
		mergeItem(distinctItemMap, purchaseItemList);
		mergeItem(distinctItemMap, jobItemList);
		
		//CONTINUE
		for(Item item: distinctItemMap.values()){
			//get bring forward for item + month + year if any
			//if yes then update
			StockBringForward sbf = stockBfService.getStockBringFowardCreateIfApplicable(month, year, item.getId());
			if(sbf == null){
				generateStockBringForward(item.getId(), month, year);
			}else{
				regenerateStockBringForward(sbf.getId());
			}
		}
	}
	
	public void mergeItem(Map<String, Item> distinctItemMap, List<Item> itemList){
		for(Item item: itemList){
			distinctItemMap.put(item.getId(), item);
		}
	}
	
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
	
	public void regenerateStockBringForward(String bfId){
		
		StockBringForward bf = stockBfService.getStockBringForwardById(bfId);
		DateTime bfDateTime = new DateTime(bf.getBfDate());
		DateTime lastMonth = bfDateTime.minusMonths(1);
		
		Long balance = getStockBalanceForItem(bf.getItem().getId(), lastMonth.getMonthOfYear(), lastMonth.getYear());
		
		LOGGER.debug("StockCheckService|regenerateStockBringForward|{}|{}|{} - {}", bf.getItem().getName(), lastMonth.getMonthOfYear(), lastMonth.getYear(), balance);
		
		bf.setUnit(balance.intValue());
		
		stockBfService.saveStockBringForward(bf);
	}
	
	public StockBringForward generateStockBringForward(String itemId, int month, int year){
		DateTime firstDayOfMonth = new DateTime(year, month, 1, 0, 0);
		DateTime lastMonth = firstDayOfMonth.minusDays(1);
		
		long balanceFromLastMonth = getStockBalanceForItem(itemId, lastMonth.getMonthOfYear(), lastMonth.getYear());
		
//		if(balanceFromLastMonth > 0){
			
			StockBringForward stockBf = new StockBringForward();
			Item item = itemService.getItemById(itemId);
			stockBf.setItem(item);
			stockBf.setBfDate(firstDayOfMonth.toDate());
			stockBf.setUnit((int)balanceFromLastMonth);
			stockBf.setCreatedDate(firstDayOfMonth.toDate());
			
			LOGGER.debug("StockCheckService|generateStockBringForward|{}|{}|{} - {}", new Object[]{item.getName(), month, year, balanceFromLastMonth});
			
			return stockBfService.saveStockBringForward(stockBf);
//		}
//		
//		return null;
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
			stockCheckList.add(new StockCheck(stockBf.getBfDate(), stockBf.getItem(), stockBf.getUnit(), StockCheck.STOCK_CHECK_TYPE.BRING_FORWARD.getValue(), desc.toString(), stockBf.getId()));
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
				transactionStockCheckList
					.add(new StockCheck(jo.getJobOrder().getJobDate(), jo.getItem(), jo.getUnit(), StockCheck.STOCK_CHECK_TYPE.JOB.getValue(), desc.toString(), jo.getJobOrder().getId(), (double)jo.getStockPrice().floatValue()));
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
