package com.lkwy.customer.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.customer.entity.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, String>{
 	
	public Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);

}
