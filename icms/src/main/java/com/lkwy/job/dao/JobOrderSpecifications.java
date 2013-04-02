package com.lkwy.job.dao;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.lkwy.customer.entity.Customer;
import com.lkwy.customer.entity.Customer_;
import com.lkwy.job.entity.JobOrder;
import com.lkwy.job.entity.JobOrder_;

public class JobOrderSpecifications {
	
	public static Specification<JobOrder> byCustomerIdAndDateRange(final String customerId, final Date dateFrom, final Date dateTo){
		return new Specification<JobOrder>(){

			@Override
			public Predicate toPredicate(Root<JobOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = cb.conjunction();
				
				Join<JobOrder, Customer> joinCustomer = root.join(JobOrder_.customer);
				p = cb.and(p, cb.equal(joinCustomer.get("id"), customerId));
				
				if(dateFrom != null){
					p = cb.and(p, cb.greaterThanOrEqualTo(root.get(JobOrder_.jobDate), dateFrom));
				}
				if(dateTo != null){
					p = cb.and(p, cb.lessThanOrEqualTo(root.get(JobOrder_.jobDate), dateTo));
				}
				
				return p;
			}
			
		};
	}
	
	public static Specification<JobOrder> byLikePoNumberPoDateVendorId(final String jobNumberOrCarPlate, final Date dateFrom, final Date dateTo){
		return new Specification<JobOrder>(){

			@Override
			public Predicate toPredicate(Root<JobOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = cb.conjunction();
				
				p = cb.and(p, cb.like(root.get(JobOrder_.jobNumber), jobNumberOrCarPlate));
				
				if(!StringUtils.equals(jobNumberOrCarPlate, "%%")){
					Join<JobOrder, Customer> joinCustomer = root.join(JobOrder_.customer);
					p = cb.or(p, cb.like(joinCustomer.get(Customer_.carPlateNumber), jobNumberOrCarPlate));
				}
				
				if(dateFrom != null){
					p = cb.and(p, cb.greaterThanOrEqualTo(root.get(JobOrder_.jobDate), dateFrom));
				}
				if(dateTo != null){
					p = cb.and(p, cb.lessThanOrEqualTo(root.get(JobOrder_.jobDate), dateTo));
				}
				
				return p;
			}
			
		};
	}

}
