package com.lkwy.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.item.dao.IItemRepository;
import com.lkwy.item.dao.ItemSpecifications;
import com.lkwy.item.entity.Item;

@Service
public class ItemService {
	
	@Autowired
	IItemRepository itemRepo;
	
	public Page<Item> getItemByCategoryIdBrandId(String categoryId, String brandId){
		Pageable pageable = new PageRequest(0, 50, Sort.Direction.ASC, "name");
		return itemRepo.findAll(ItemSpecifications.byCategoryIdBrandId(categoryId, brandId), pageable);
	}
	
	public Item getItemById(String id){
		return itemRepo.findOne(id);
	}
	
	public void deleteItem(String id){
		itemRepo.delete(id);
	}
	
	public Item saveItem(Item item) throws DataIntegrityViolationException, Exception{
		return itemRepo.save(item);
	}
	
	public Page<Item> getItemByNameCodeCategoryId(String input, String categoryId, String brandId, Pageable pageable){
		String inputWildCard = CommonUtil.addWildCard(input);
		return itemRepo.findAll(ItemSpecifications.byLikeNameCategoryIdBrandId(inputWildCard, categoryId, brandId), pageable);
	}

}
