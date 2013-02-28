package com.lkwy.item.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.item.entity.Item;

public interface IItemRepository extends JpaRepository<Item, String>{
	
	public Page<Item> findAll(Specification<Item> spec, Pageable page);
	
}
