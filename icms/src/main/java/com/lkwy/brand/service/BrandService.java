package com.lkwy.brand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lkwy.brand.dao.IBrandRepository;
import com.lkwy.brand.entity.Brand;

@Service
public class BrandService {
	
	@Autowired
	IBrandRepository brandRepo;
	
	public List<Brand> getAllBrand(){
		return brandRepo.findAll(new Sort(Sort.DEFAULT_DIRECTION.ASC, "name"));
	}
	
	public Brand addNewBrand(Brand brand){
		return brandRepo.save(brand);
	}

}
