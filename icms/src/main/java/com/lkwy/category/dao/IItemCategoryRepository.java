package com.lkwy.category.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lkwy.category.entity.ItemCategory;

@Transactional(readOnly=true)
public interface IItemCategoryRepository extends JpaRepository<ItemCategory, String>{
	
	@Query("SELECT ic FROM ItemCategory ic WHERE ic.name LIKE ?1")
	public Page<ItemCategory> findByLikeName(String name, Pageable pageable);
	

}
