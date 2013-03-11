package com.lkwy.brand.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.brand.entity.Brand;

public interface IBrandRepository extends JpaRepository<Brand, String>{
	
	public List<Brand> findAll(Sort sort);

}
