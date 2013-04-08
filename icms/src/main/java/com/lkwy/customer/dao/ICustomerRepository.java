package com.lkwy.customer.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lkwy.customer.entity.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, String>{
 	
	public Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);
	
	public Customer findOneByCarPlateNumber(String carPlateNumber);
	
	@Query("SELECT c FROM Customer c WHERE carPlateNumber LIKE ?1")
	public List<Customer> findByLikeCarPlateNumber(String carPlateNumber, Sort sort);
	
}
