package com.lkwy.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lkwy.category.dao.IItemCategoryRepository;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.common.util.CommonUtil;

@Service
public class ItemCategoryService {
	
	@Autowired
	IItemCategoryRepository itemCatRepository;
	
	public List<ItemCategory> getAll(Sort sort){
		return itemCatRepository.findAll();
	}
	
	public void deleteCategory(String id){
		itemCatRepository.delete(id);
	}
	
	public ItemCategory saveCategory(ItemCategory category){
		return itemCatRepository.save(category);
	}
	
	public ItemCategory getCategoryById(String id){
		return itemCatRepository.findOne(id);
	}
	
	public Page<ItemCategory> getCategoryByName(String name, Pageable pageable){
		String nameWildCard = CommonUtil.addWildCard(name);
		return itemCatRepository.findByLikeName(nameWildCard, pageable);
	}

}
