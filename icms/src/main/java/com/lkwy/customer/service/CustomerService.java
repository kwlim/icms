package com.lkwy.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.customer.dao.CustomerSpecifications;
import com.lkwy.customer.dao.ICustomerRepository;
import com.lkwy.customer.entity.Customer;

@Service
public class CustomerService {
	
	@Autowired
	ICustomerRepository customerRepo;
	
	public List<Customer> getAllCustomer(){
		return customerRepo.findAll(new Sort(Sort.Direction.ASC, "carPlateNumber"));
	}
	
	public void deleteCustomer(String id){
		customerRepo.delete(id);
	}
	
	public Customer getCustomerById(String id){
		return customerRepo.findOne(id);
	}
	
	public Customer saveCustomer(Customer customer){
		return customerRepo.save(customer);
	}
	
	public Page<Customer> getCustomerByCarPlateNameContact(String input, Pageable pageable){
		String inputWildcard = CommonUtil.addWildCard(input);
		return customerRepo.findAll(CustomerSpecifications.byLikeCompanyNameContactPersonContactNumber(inputWildcard), pageable);
	}
	
}
