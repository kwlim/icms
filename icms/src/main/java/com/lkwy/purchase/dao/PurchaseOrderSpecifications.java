package com.lkwy.purchase.dao;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.lkwy.purchase.entity.PurchaseOrder;
import com.lkwy.purchase.entity.PurchaseOrder_;
import com.lkwy.vendor.entity.Vendor;

public class PurchaseOrderSpecifications {
	
	public static Specification<PurchaseOrder> byVendorIdAndDateRange(final String vendorId, final Date dateFrom, final Date dateTo){
		return new Specification<PurchaseOrder>(){

			@Override
			public Predicate toPredicate(Root<PurchaseOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = cb.conjunction();
				
				Join<PurchaseOrder, Vendor> joinVendor = root.join(PurchaseOrder_.vendor);
				p = cb.and(p, cb.equal(joinVendor.get("id"), vendorId));
				
				if(dateFrom != null){
					p = cb.and(p, cb.greaterThanOrEqualTo(root.get(PurchaseOrder_.poDate), dateFrom));
				}
				if(dateTo != null){
					p = cb.and(p, cb.lessThanOrEqualTo(root.get(PurchaseOrder_.poDate), dateTo));
				}
				
				return p;
			}
			
		};
	}
	
	public static Specification<PurchaseOrder> byLikePoNumberPoDateVendorId(final String poNumber, final Date dateFrom, final Date dateTo, final String vendorId){
		return new Specification<PurchaseOrder>(){

			@Override
			public Predicate toPredicate(Root<PurchaseOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = cb.conjunction();
				
				p = cb.and(p, cb.like(root.get(PurchaseOrder_.poNumber), poNumber));
				
				if(dateFrom != null){
					p = cb.and(p, cb.greaterThanOrEqualTo(root.get(PurchaseOrder_.poDate), dateFrom));
				}
				if(dateTo != null){
					p = cb.and(p, cb.lessThanOrEqualTo(root.get(PurchaseOrder_.poDate), dateTo));
				}
				
				if(!StringUtils.isEmpty(vendorId)){
					Join<PurchaseOrder, Vendor> joinVendor = root.join(PurchaseOrder_.vendor);
					p = cb.and(p, cb.equal(joinVendor.get("id"), vendorId));
				}
				
				return p;
			}
			
		};
	}

}
