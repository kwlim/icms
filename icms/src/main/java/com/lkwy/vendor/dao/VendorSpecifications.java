package com.lkwy.vendor.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.lkwy.vendor.entity.Vendor;
import com.lkwy.vendor.entity.Vendor_;

public class VendorSpecifications {
	
	public static Specification<Vendor> byLikeCompanyNameContactPersonContactNumber(final String input){
		return new Specification<Vendor>(){

			@Override
			public Predicate toPredicate(Root<Vendor> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.and(cb.like(root.get(Vendor_.companyName), input));
				p = cb.or(p, cb.like(root.get(Vendor_.contactNumber), input));
				p = cb.or(p, cb.like(root.get(Vendor_.contactPerson), input));
				
				return p;
			}
			
		};
	}

}
