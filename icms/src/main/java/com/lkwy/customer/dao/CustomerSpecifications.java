package com.lkwy.customer.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.lkwy.customer.entity.Customer;
import com.lkwy.customer.entity.Customer_;

public class CustomerSpecifications {
	
	public static Specification<Customer> byLikeCompanyNameContactPersonContactNumber(final String input){
		return new Specification<Customer>(){

			@Override
			public Predicate toPredicate(Root<Customer> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.and(cb.like(root.get(Customer_.carPlateNumber), input));
				p = cb.or(p, cb.like(root.get(Customer_.contactNumber), input));
				p = cb.or(p, cb.like(root.get(Customer_.name), input));
				
				return p;
			}
			
		};
	}

}
