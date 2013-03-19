package com.lkwy.item.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.lkwy.brand.entity.Brand;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.item.entity.Item;
import com.lkwy.item.entity.Item_;

public class ItemSpecifications {
	
	public static Specification<Item> byCategoryIdBrandId(final String categoryId, final String brandId){
		return new Specification<Item>(){
			
			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = cb.conjunction();
				
				if(!StringUtils.isEmpty(categoryId)){
					Join<Item, ItemCategory> join = root.join(Item_.category);
					p = cb.and(p, cb.equal(join.get("id"), categoryId));
				}
				if(!StringUtils.isEmpty(brandId)){
					Join<Item, Brand> join = root.join(Item_.brand);
					p = cb.and(p, cb.equal(join.get("id"), brandId));
				}
				
				return p;
			}
			
		};
	}
	
	public static Specification<Item> byLikeNameCategoryIdBrandId(final String input, final String categoryId, final String brandId){
		return new Specification<Item>(){
			
			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = cb.and(cb.like(root.get(Item_.name), input));
				p = cb.or(p, cb.like(root.get(Item_.code), input));
				
				if(!StringUtils.isEmpty(categoryId)){
					Join<Item, ItemCategory> join = root.join(Item_.category);
					p = cb.and(p, cb.equal(join.get("id"), categoryId));
				}
				if(!StringUtils.isEmpty(brandId)){
					Join<Item, Brand> join = root.join(Item_.brand);
					p = cb.and(p, cb.equal(join.get("id"), brandId));
				}
				
				return p;
			}
			
		};
	}

}
