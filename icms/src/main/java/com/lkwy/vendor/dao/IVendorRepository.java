package com.lkwy.vendor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.vendor.entity.Vendor;

public interface IVendorRepository extends JpaRepository<Vendor, String>{
	
	public Page<Vendor> findAll(Specification<Vendor> spec, Pageable pageable);
	
}
