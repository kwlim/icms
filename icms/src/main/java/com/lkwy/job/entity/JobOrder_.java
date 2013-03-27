package com.lkwy.job.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.lkwy.customer.entity.Customer;

@StaticMetamodel(JobOrder.class)
public class JobOrder_ {
	
	public static volatile SingularAttribute<JobOrder, String> jobNumber;
	public static volatile SingularAttribute<JobOrder, Date> jobDate;
	public static volatile SingularAttribute<JobOrder, Customer> customer;
	public static volatile ListAttribute<JobOrder, JobItem> jobItemList;
	
}
