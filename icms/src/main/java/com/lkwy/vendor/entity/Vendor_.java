package com.lkwy.vendor.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Vendor.class)
public class Vendor_ {
	
	public static volatile SingularAttribute<Vendor, String> companyName;
	public static volatile SingularAttribute<Vendor, String> contactPerson;
	public static volatile SingularAttribute<Vendor, String> contactNumber;

}
